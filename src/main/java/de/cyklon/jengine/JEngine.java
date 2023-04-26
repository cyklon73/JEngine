package de.cyklon.jengine;

import de.cyklon.jengine.engine.BaseEngine;
import de.cyklon.jengine.engine.Engine;
import de.cyklon.jengine.event.Event;
import de.cyklon.jengine.event.EventDispatcher;
import de.cyklon.jengine.render.Canvas;
import de.cyklon.jengine.render.DefaultCanvas;
import de.cyklon.jengine.render.Frame;
import de.cyklon.jengine.render.Panel;
import de.cyklon.jengine.resource.IResourceManager;
import de.cyklon.jengine.resource.Resource;
import de.cyklon.jengine.resource.ResourceManager;
import de.cyklon.jengine.util.FinalObject;
import de.cyklon.jengine.util.Task;
import de.cyklon.jengine.util.Vector;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static String name;
    private static Frame frame;
    private static EventDispatcher eventDispatcher;
    private static Logger logger;
    private static IResourceManager resourceManager;
    private static Configuration configuration;
    private static Canvas canvas;
    private static boolean canvasInitalized;

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
        JEngine jEngine = createEngine();
        engine = new BaseEngine(jEngine);
        canvas = new DefaultCanvas();
        eventDispatcher = new EventDispatcher(engine);
        canvasInitalized = false;
        UPDATE_RATE = 60.0;
        UPDATE_INTERVAL = 1000000000 / UPDATE_RATE;

        logger.info("init frame");
        frame = new Frame();
        setNameLocal(configuration.getString("name"));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Panel panel = new Panel(jEngine);
        frame.add(panel);

        frame.addMouseMotionListener(eventDispatcher);
        frame.addMouseWheelListener(eventDispatcher);
        frame.addMouseListener(eventDispatcher);
        frame.addKeyListener(eventDispatcher);

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

    private static void gameLoop() {
        running = true;
        long lastUpdateTime = System.nanoTime();
        while (running) {
            long current = System.nanoTime();
            long elapsed = current - lastUpdateTime;
            updateFPS();

            if (!canvasInitalized) {
                canvas.init();
                canvasInitalized = true;
            }

            if (UPDATE_RATE<0 || elapsed >= UPDATE_INTERVAL) {
                lastUpdateTime = current;
                deltaTime = elapsed / 1000000000.0;

                frame.repaint();
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

    private static void setNameLocal(String name) {
        JEngine.name = name;
        frame.setName(name);
        frame.setTitle(name);
    }

    private void setName(String name) {
        check();
        setNameLocal(name);
    }

    public String getName() {
        return name;
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
        registerEvent(c);
        canvasInitalized = false;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setGraphics(Graphics2D g) {
        graphics = g;
        canvas.loop();
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

    public FontMetrics getFontMetrics() {
        return graphics.getFontMetrics();
    }

    public FontMetrics getFontMetrics(Font font) {
        return graphics.getFontMetrics(font);
    }

    public void setMaxSize(Dimension max) {
        check();
        frame.setMaximumSize(max);
    }

    public void setMinSize(Dimension min) {
        check();
        frame.setMinimumSize(min);
    }

    public void dispose() {
        check();
        frame.dispose();
    }

    public void setIcon(Resource resource) throws IOException {
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

}
