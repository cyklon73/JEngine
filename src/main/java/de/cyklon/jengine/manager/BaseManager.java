package de.cyklon.jengine.manager;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.event.Event;
import de.cyklon.jengine.render.IFontRenderer;
import de.cyklon.jengine.render.IShapeRenderer;
import de.cyklon.jengine.render.Renderer;
import de.cyklon.jengine.render.sprite.ISpriteRenderer;
import de.cyklon.jengine.render.sprite.SpriteBaseRenderer;
import de.cyklon.jengine.resource.Resource;
import de.cyklon.jengine.math.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class BaseManager {

    private static JEngine engine;

    public static void setup(JEngine engine) {
        BaseManager.engine = engine;
    }


    public static class IGraphicsManager implements GraphicsManager {

        @Override
        public boolean useVsync() {
            return engine.useVsync();
        }

        @Override
        public void setVsync(boolean vsync) {
            engine.setVsync(vsync);
        }

        @Override
        public double getMaxFPS() {
            return engine.getMaxFPS();
        }

        @Override
        public void setMaxFPS(double fps) {
            engine.setMaxFPS(fps);
        }

        @Override
        public int getFPS() {
            return (int) Math.round(getFPSasDouble());
        }

        @Override
        public double getFPSasDouble() {
            return 1.0 / JEngine.getEngine().getDeltaTime();
        }

        @Override
        public WindowManager getWindowManager() {
            return new IWindowManager();
        }

        @Override
        public IFontRenderer getFontRenderer() {
            return new Renderer.FontRenderer();
        }

        @Override
        public IShapeRenderer getShapeRenderer() {
            return new Renderer.ShapeRenderer();
        }

        @Override
        public ISpriteRenderer getSpriteRenderer() {
            return SpriteBaseRenderer.getSpriteRenderer();
        }

        @Override
        public void setColor(Color color) {
            engine.setRenderColor(color);
        }

        @Override
        public Dimension getScreenSize() {
            return Toolkit.getDefaultToolkit().getScreenSize();
        }

        @Override
        public void dispose() {
            engine.dispose();
        }

        @Override
        public BufferedImage takeScreenshot() {
            return engine.takeScreenshot();
        }
    }

    public static class IWindowManager implements WindowManager {

        @Override
        public void setDimension(Dimension dimension) {
            engine.setDimension(dimension);
        }

        @Override
        public Dimension getDimension() {
            return engine.getDimension();
        }

        @Override
        public void setLocation(Vector vec) {
            engine.setLocation(vec);
        }

        @Override
        public Vector getLocation() {
            return engine.getLocation();
        }

        @Override
        public void setVisible(boolean visible) {
            engine.setVisible(visible);
        }

        @Override
        public boolean isVisible() {
            return engine.isVisible();
        }

        @Override
        public void setResizable(boolean resizable) {
            engine.setResizable(resizable);
        }

        @Override
        public boolean isResizable() {
            return engine.isResizable();
        }

        @Override
        public void hideTitleBar(boolean hiding) {
            engine.hideTitleBar(hiding);
        }

        @Override
        public boolean isTitleBarHidden() {
            return engine.isTitleBarHidden();
        }

        @Override
        public void setDraggableBackground(boolean draggable) {
            engine.setDraggableBackground(draggable);
        }

        @Override
        public boolean isBackgroundDraggable() {
            return engine.isBackgroundDraggable();
        }

        @Override
        public void setIcon(Resource resource) throws IOException {
            engine.setIcon(resource);
        }

        @Override
        public void setMaxSize(Dimension max) {
            engine.setMaxSize(max);
        }

        @Override
        public void setMinSize(Dimension min) {
            engine.setMinSize(min);
        }

        @Override
        public Dimension getMaxSize() {
            return engine.getMaxSize();
        }

        @Override
        public Dimension getMinSize() {
            return engine.getMinSize();
        }

        @Override
        public void setToCenter() {
            Dimension d1 = Toolkit.getDefaultToolkit().getScreenSize(), d2 = engine.getDimension();
            double w1 = d1.getWidth(), h1 = d1.getHeight(), w2 = d2.getWidth(), h2 = d2.getHeight();
            setLocation(new Vector(w1/2-w2/2, h1/2-h2/2));
        }
    }

    public static class IEventManager implements EventManager {

        @Override
        public void registerEvents(Event event) {
            engine.registerEvent(event);
        }

        @Override
        public <T> List<T> getEvents(Class<T> eventClass) {
            return engine.getEvents(eventClass);
        }
    }

}
