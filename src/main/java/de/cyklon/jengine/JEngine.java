package de.cyklon.jengine;

import de.cyklon.jengine.engine.BaseEngine;
import de.cyklon.jengine.engine.Engine;
import de.cyklon.jengine.event.Event;
import de.cyklon.jengine.render.Canvas;
import de.cyklon.jengine.render.DefaultCanvas;
import de.cyklon.jengine.render.Frame;
import de.cyklon.jengine.render.Panel;
import de.cyklon.jengine.util.FinalObject;
import de.cyklon.jengine.util.Vector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import javax.swing.*;
import java.awt.*;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class JEngine {

    private static boolean vsync = false;
    private static final FinalObject<Boolean> initalized = new FinalObject<>(2, false);
    private static final List<Event> events = new ArrayList<>();
    private static boolean running = false;

    private static double deltaTime;
    private static double UPDATE_RATE, UPDATE_INTERVAL, FPS;
    private static Graphics2D graphics;
    private static final FinalObject<Thread> gameThread = new FinalObject<>();
    private static Frame frame;
    private static Logger logger;

    private static Canvas canvas;

    private JEngine() {

    }

    private static JEngine createEngine() {
        return new JEngine();
    }

    private static Engine engine;

    /**
     * call this method in your main method at the top
     * @param name the name of your game
     */
    public static void start(String name) {
        JEngine jEngine = createEngine();
        engine = new BaseEngine(jEngine);
        canvas = new DefaultCanvas();
        UPDATE_RATE = 60.0;
        UPDATE_INTERVAL = 1000000000 / UPDATE_RATE;
        logger = LogManager.getLogger(name);
        Configurator.initialize(name, "src/main/resources/log4j.xml");
        logger.info("init frame");
        frame = new Frame();
        frame.setName(name);
        frame.setTitle(name);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Panel panel = new Panel(jEngine);
        frame.add(panel);

        //frame.pack();

        logger.info("init game loop");
        gameThread.set(new Thread(JEngine::gameLoop));

        gameThread.get().start();
        initalized.set(true);
    }

    private static void gameLoop() {
        running = true;
        long lastUpdateTime = System.nanoTime();
        while (running) {
            long current = System.nanoTime();
            long elapsed = current - lastUpdateTime;
            updateFPS();

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

    public FontMetrics getFontMetrics() {
        return graphics.getFontMetrics();
    }

    public FontMetrics getFontMetrics(Font font) {
        return graphics.getFontMetrics(font);
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
