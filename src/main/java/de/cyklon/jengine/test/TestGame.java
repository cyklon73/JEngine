package de.cyklon.jengine.test;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.engine.Engine;
import de.cyklon.jengine.manager.GraphicsManager;
import de.cyklon.jengine.manager.WindowManager;
import de.cyklon.jengine.render.Canvas;

import java.awt.*;

public class TestGame {

    public static void main(String[] args) throws InterruptedException {
        JEngine.start("test_game");
        Engine engine = JEngine.getEngine();
        GraphicsManager gm = engine.getGraphicsManager();
        WindowManager wm = gm.getWindowManager();
        wm.setDimension(new Dimension(600, 400));
        wm.hideTitleBar(true);
        wm.setDraggableBackground(true);
        wm.setVisible(true);

        gm.setMaxFPS(100);

        engine.setCanvas(new Menu());

        Thread.sleep(5000);
        gm.setVsync(true);
        Thread.sleep(5000);
        gm.setVsync(false);
    }

    private static class Menu extends Canvas {

        private GraphicsManager getGraphisManager() {
            return getEngine().getGraphicsManager();
        }

        @Override
        public void loop() {
            getLogger().info("FPS: " + getGraphisManager().getFPS());
        }
    }

}
