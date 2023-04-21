package de.cyklon.jengine.manager;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.render.IFontRenderer;
import de.cyklon.jengine.render.Renderer;
import de.cyklon.jengine.util.Vector;

import java.awt.*;

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
        public void setColor(Color color) {
            engine.setRenderColor(color);
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
    }

}
