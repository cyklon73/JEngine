package de.cyklon.jengine;

import de.cyklon.jengine.engine.BaseEngine;
import de.cyklon.jengine.engine.Engine;
import de.cyklon.jengine.event.Event;
import de.cyklon.jengine.event.EventDispatcher;
import de.cyklon.jengine.gameobject.AbstractGameObject;
import de.cyklon.jengine.gameobject.GameObject;
import de.cyklon.jengine.input.Cursor;
import de.cyklon.jengine.input.Keyboard;
import de.cyklon.jengine.input.Mouse;
import de.cyklon.jengine.render.*;
import de.cyklon.jengine.render.Canvas;
import de.cyklon.jengine.render.Frame;
import de.cyklon.jengine.render.Panel;
import de.cyklon.jengine.resource.IResourceManager;
import de.cyklon.jengine.resource.Resource;
import de.cyklon.jengine.resource.ResourceManager;
import de.cyklon.jengine.util.FinalObject;
import de.cyklon.jengine.util.Task;
import de.cyklon.jengine.math.Vector;
import de.cyklon.jengine.util.TryCatch;
import lombok.Getter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.AttributedCharacterIterator;
import java.util.*;
import java.util.List;

public class JEngine {

    private static boolean vsync = false;
    private static final FinalObject<Boolean> initalized = new FinalObject<>(2, false);
    private static Class<?> gameClass;
    private static final List<Event> events = new ArrayList<>();
    private static final Map<Long, Task> tasks = new HashMap<>();
    private static long taskID = 0;
    private static boolean running = false;

    private static double deltaTime;
    private static double UPDATE_RATE, UPDATE_INTERVAL, FPS;
    private static Graphics2D graphics;
    private static final FinalObject<Thread> gameThread = new FinalObject<>();
    private static List<GameObject> gameObjects;

    @Getter
    private static String name;
    private static Frame frame;
    private static EventDispatcher eventDispatcher;
    private static Logger logger;
    private static IResourceManager resourceManager;
    private static Configuration configuration;
    private static Canvas canvas;
    private static boolean canvasInitalized;

    private static long tickTime;
    private static List<ActionListener> tickActions;
    private static boolean cursorFixed;
    private static Robot robot;

    private JEngine() {

    }

    private static JEngine createEngine() {
        return new JEngine();
    }

    private static Engine engine;

    public static synchronized void start() throws ClassNotFoundException, URISyntaxException {
        gameClass = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
        start(new Configuration());
    }

    public static long gtt() {
        return tickTime;
    }

    /**
     * call this method in your main method at the top
     */
    public static synchronized void start(Configuration configuration) throws ClassNotFoundException, URISyntaxException {
        if (gameClass==null) gameClass = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
        configuration.mergeDefault(getDefaultConfiguration());
        logger = LogManager.getLogger(gameClass);
        ClassLoader classLoader = JEngine.class.getClassLoader();
        LoggerContext context = Configurator.initialize(gameClass.getSimpleName(), classLoader, classLoader.getResource("log4j.xml").toURI());
        context.getConfiguration().getLoggerConfig(logger.getName()).setLevel((Level) configuration.get("log.level"));
        context.updateLoggers();
        logger.info("init JEngine");
        tickTime = 0;
        tickActions = new ArrayList<>();
        gameObjects = new ArrayList<>();
        cursorFixed = false;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            logger.error(e);
        }
        JEngine jEngine = createEngine();
        engine = new BaseEngine(jEngine);
        canvas = new DefaultCanvas();
        eventDispatcher = new EventDispatcher(engine);
        canvasInitalized = false;
        UPDATE_RATE = 60.0;
        UPDATE_INTERVAL = 1000000000 / UPDATE_RATE;
        graphics = new EmptyGraphics();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown(jEngine)));
        AbstractGameObject.engine(jEngine);

        logger.info("init frame");
        frame = new Frame();
        setNameLocal(configuration.getString("name"));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Panel panel = new Panel(jEngine);
        frame.add(panel);

        //frame.addMouseMotionListener(mouseAdapter());
        //frame.addMouseListener(mouseAdapter());

        frame.addMouseMotionListener(eventDispatcher);
        frame.addMouseWheelListener(eventDispatcher);
        frame.addMouseListener(eventDispatcher);
        frame.addKeyListener(eventDispatcher);

        logger.info("init input");
        logger.info("init keyboard");
        InputMap inputMap = frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = frame.getRootPane().getActionMap();
        Keyboard keyboard = new Keyboard(jEngine);
        tickActions.add(keyboard.tick());
        for (Field field : KeyEvent.class.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) && field.getType() == int.class) {
                try {
                    int keyCode = field.getInt(null);
                    KeyStroke keyStroke = KeyStroke.getKeyStroke(keyCode, 0, false);
                    KeyStroke keyStroke_release = KeyStroke.getKeyStroke(keyCode, 0, true);

                    inputMap.put(keyStroke, keyCode);
                    actionMap.put(keyCode, keyboard.pressed());
                    inputMap.put(keyStroke_release, "r" + keyCode);
                    actionMap.put("r" + keyCode, keyboard.released());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        logger.info("init mouse");
        new Mouse(jEngine.getCursor());

        logger.info("init ResourceManager");
        resourceManager = ResourceManager.getInstance(jEngine);

        logger.info("init game loop");
        gameThread.set(new Thread(JEngine::gameLoop));

        gameThread.get().start();
        logger.info("initialization complete");
        initalized.set(true);
    }

    private static Configuration getDefaultConfiguration() {
        Configuration defaultConfiguration = new Configuration();
        defaultConfiguration.configure("name", gameClass.getSimpleName());
        defaultConfiguration.configure("log.level", Level.ALL);
        return defaultConfiguration;
    }

    private static void shutdown(JEngine engine) {
        engine.getLogger().info("Shutting down");
    }

    private static void gameLoop() {
        running = true;
        long lastUpdateTime = System.nanoTime();
        while (running) {
            long current = System.nanoTime();
            long elapsed = current - lastUpdateTime;
            updateFPS();
            if (cursorFixed) centerCursor();

            if (!canvasInitalized) {
                canvas.init();
                canvasInitalized = true;
                TryCatch.tryCatch(() -> Thread.sleep(50));
            } else {
                if (UPDATE_RATE<0 || elapsed >= UPDATE_INTERVAL) {
                    tickTime++;
                    tickActions.forEach((a) -> a.actionPerformed(new ActionEvent(JEngine.class, 0, "new:" + tickTime)));
                    lastUpdateTime = current;
                    deltaTime = elapsed / 1000000000.0;

                    frame.repaint();
                    tickActions.forEach((a) -> a.actionPerformed(new ActionEvent(JEngine.class, 0, "finished:" + tickTime)));
                }
            }
        }
    }

    private static void check() {
        if (!initalized.get()) throw new IllegalStateException("You have to start the engine first!");
    }

    /**
     * @return the Engine interface
     */
    public static Engine getEngine() {
        check();
        return engine;
    }

    private static MouseAdapter mouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (cursorFixed) centerCursor();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (cursorFixed) centerCursor();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (cursorFixed) centerCursor();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (cursorFixed) centerCursor();
            }
        };
    }

    private static void centerCursor() {
        if (robot==null) return;
        Vector v = getEngine().getGraphicsManager().getWindowManager().getLocation();
        Dimension d = getEngine().getGraphicsManager().getWindowManager().getDimension();
        robot.mouseMove((int) (v.getX()+d.getWidth()/2f), (int) (v.getY()+d.getHeight()/2f));
    }

    private static void setNameLocal(String name) {
        JEngine.name = name;
        frame.setName(name);
        frame.setTitle(name);
    }

    private void setName(String name) {
        check();
        setNameLocal(name);
    }

    public IResourceManager getResourceManager() {
        return resourceManager;
    }

    private static void updateFPS() {
        GraphicsDevice screen = getScreen();
        UPDATE_RATE = vsync ? (screen==null ? FPS : screen.getDisplayMode().getRefreshRate()) : FPS;
        UPDATE_INTERVAL = 1000000000 / UPDATE_RATE;
    }

    public void setMaxFPS(double fps) {
        check();
        FPS = fps;
    }

    public double getMaxFPS() {
        check();
        return UPDATE_RATE;
    }

    public boolean useVsync() {
        check();
        return vsync;
    }

    public void setVsync(boolean vsync) {
        check();
        JEngine.vsync = vsync;
    }

    public Dimension getDimension() {
        check();
        return frame.getSize();
    }

    public void  setDimension(Dimension dimension) {
        check();
        frame.setSize(dimension);
        frame.setPreferredSize(dimension);
    }

    public Vector getLocation() {
        check();
        return new Vector(frame.getLocation());
    }

    public void setLocation(Vector loc) {
        check();
        frame.setLocation(loc.toPoint());
    }

    public void setVisible(boolean visible) {
        check();
        frame.setVisible(visible);
    }

    public boolean isVisible() {
        check();
        return frame.isVisible();
    }

    public double getDeltaTime() {
        check();
        return deltaTime;
    }

    public Logger getLogger() {
        check();
        return logger;
    }

    public void setResizable(boolean resizable) {
        check();
        frame.setResizable(resizable);
    }

    public boolean isResizable() {
        check();
        return frame.isResizable();
    }

    public void hideTitleBar(boolean hiding) {
        check();
        frame.setUndecorated(hiding);
    }

    public boolean isTitleBarHidden() {
        check();
        return frame.isUndecorated();
    }

    public void setDraggableBackground(boolean draggable) {
        check();
        frame.setDraggableBackground(draggable);
    }

    public boolean isBackgroundDraggable() {
        check();
        return frame.isBackgroundDraggable();
    }

    public void setCanvas(Canvas c) {
        check();
        canvas = c;
        canvasInitalized = false;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setGraphics(Graphics2D g) {
        graphics.dispose();
        graphics = g;
        canvas.loop();
        updateGameObjects();
    }

    public void drawString(String str, float x, float y) {
        graphics.drawString(str, x, y);
    }

    public void drawString(AttributedCharacterIterator iterator, float x, float y) {
        graphics.drawString(iterator, x, y);
    }

    public void setFont(Font font) {
        graphics.setFont(font);
    }

    public Font getFont() {
        return graphics.getFont();
    }

    public void setRenderColor(Color color) {
        graphics.setColor(color);
    }

    public void setRotation(double angle) {
        graphics.rotate(Math.toRadians(angle), 100, 0);
    }

    public void setRotationPoint(int x, int y) {
        //graphics.(x, y);
    }

    public Color getRenderColor() {
        return graphics.getColor();
    }

    private void draw(Shape shape, boolean filled) {
       if (filled) graphics.fill(shape);
       else graphics.draw(shape);
    }

    public void drawRect(int x, int y, int width, int height, boolean filled) {
        draw(new Rectangle(x, y, width, height), filled);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        graphics.drawLine(x1, y1, x2, y2);
    }

    public void drawOval(int x, int y, int width, int height, boolean filled) {
        graphics.drawOval(x, y, width, height);
    }

    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints, boolean filled) {
        draw(new Polygon(xPoints, yPoints, nPoints), filled);
    }

    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints, boolean filled) {
        graphics.drawPolyline(xPoints, yPoints, nPoints);
    }

    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle, boolean filled) {
        graphics.drawArc(x, y, width, height, startAngle, arcAngle);
    }

    public void drawImage(Image img, int x, int y) {
        graphics.drawImage(img, x, y, getRenderColor(), null);
    }

    public void drawImage(Image img, int x, int y, int width, int height) {
        graphics.drawImage(img, x, y, width, height, getRenderColor(), null);
    }


    public FontMetrics getFontMetrics() {
        return graphics.getFontMetrics();
    }

    public FontMetrics getFontMetrics(Font font) {
        return graphics.getFontMetrics(font);
    }

    public void setMaxSize(Dimension max) {
        check();
        frame.setMaximumSize(max);
        frame.setMaximizedBounds(new Rectangle());
    }

    public void setMinSize(Dimension min) {
        check();
        frame.setMinimumSize(min);
    }

    public Dimension getMaxSize() {
        check();
        return frame.getMaximumSize();
    }

    public Dimension getMinSize() {
        check();
        return frame.getMinimumSize();
    }

    public void dispose() {
        check();
        frame.dispose();
    }

    public void setIcon(Resource resource) throws IOException {
        check();
        try {
            frame.setIconImage(new ImageIcon(resource.getBytes(), "").getImage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public long getNextTaskID() {
        final long id = taskID;
        taskID++;
        return id;
    }

    public void addTask(Task task) {
        tasks.put(task.getID(), task);
        task.getThread().start();
    }

    public void cancelTask(long id) {
        Task task = tasks.get(id);
        if (task!=null) {
            task.getThread().interrupt();
            removeTask(id);
        }
    }
    public void removeTask(long id) {
        Task task = tasks.get(id);
        if (task!=null) task.finished();
        tasks.remove(id);
    }

    public Task getTask(long id) {
        return tasks.get(id);
    }



    public void registerEvent(Event event) {
        events.add(event);
    }

    public  <T> List<T> getEvents(Class<T> eventClass) {
        if (eventClass.isInstance(Event.class)) throw new IllegalArgumentException("eventClass must be a interface extends of the event interface");
        List<T> result = new ArrayList<>();
        for (Event e : events) {
            if (isEvent(e, eventClass)) result.add((T) e);
        }
        if (isEvent(canvas, eventClass)) result.add((T) canvas);
        return result;
    }

    private <T> boolean isEvent(Event event, Class<T> clazz) {
        Class<?>[] interfaces = event.getClass().getInterfaces();
        for (Class<?> i : interfaces) {
            if (Event.class.isAssignableFrom(i)) {
                return i.equals(clazz);
            }
        }
        return false;
    }

    public long getTickTime() {
        return tickTime;
    }

    private void updateGameObjects() {
        gameObjects.forEach((obj) -> {
            if (obj instanceof AbstractGameObject abstractObject) abstractObject.runUpdate();
        });
    }

    public void registerGameObject(GameObject obj) {
        gameObjects.add(obj);
        gameObjects.sort(Comparator.comparingInt(o -> o.getLayer().getLayer()));
    }

    public BufferedImage takeScreenshot() {
        Component comp = frame;
        Dimension size = comp.getSize();
        BufferedImage image = new BufferedImage(
                size.width, size.height
                , BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        comp.paintAll(g2);
        return image;
    }

    private static GraphicsDevice getScreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();

        int frameX = frame.getX();
        int frameY = frame.getY();

        for (GraphicsDevice screen : screens) {
            if (screen.getDefaultConfiguration().getBounds().contains(frameX, frameY)) {
                return screen;
            }
        }

        return null;
    }

    public Cursor getCursor() {
        return new Cursor() {

            @Override
            public Vector getCursorPosition() {
                return new Vector(MouseInfo.getPointerInfo().getLocation());
            }

            @Override
            public Vector getCursorPositionRelative() {
                Vector pos = getCursorPosition();
                pos.subtract(getLocation());
                if (!isTitleBarHidden()) pos.subtract(new Vector(7, 30));
                return pos;
            }

            @Override
            public void setHidden(boolean hidden) {
                Toolkit tk = Toolkit.getDefaultToolkit();
                if (hidden) frame.getContentPane().setCursor(tk.createCustomCursor(tk.getImage(""), new Point(), "blank"));
                else frame.getContentPane().setCursor(java.awt.Cursor.getDefaultCursor());
            }

            @Override
            public boolean isHidden() {
                return frame.getContentPane().getCursor().getName().equals("blank");
            }

            @Override
            public void setFixed(boolean fixed) {
                JEngine.cursorFixed = fixed;
            }

            @Override
            public boolean isFixed() {
                return cursorFixed;
            }
        };
    }
}
