package de.cyklon.jengine.gameobject.physic;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.engine.Engine;
import de.cyklon.jengine.gameobject.Light;
import de.cyklon.jengine.gameobject.Text;
import de.cyklon.jengine.manager.GraphicsManager;
import de.cyklon.jengine.manager.WindowManager;
import de.cyklon.jengine.math.Vector;
import de.cyklon.jengine.render.canvas.Canvas;
import de.cyklon.jengine.resource.IResourceManager;

import java.awt.*;
import java.net.URISyntaxException;

public class TestPhysicGame {

    public static void main(String[] args) throws URISyntaxException, ClassNotFoundException, InterruptedException {
        JEngine.start();
        Engine engine = JEngine.getEngine();

        IResourceManager resourceManager = engine.getResourceManager();
        GraphicsManager gm = engine.getGraphicsManager();
        WindowManager wm = gm.getWindowManager();

        engine.setCanvas(new Can());

        wm.setDimension(new Dimension(500, 500));
        wm.setToCenter();
        wm.setVisible(true);

    }

    private static class Can extends Canvas {

        Polygon pol = null;
        @Override
        public void init() {
            //new Polygon().register();
            new Light(Light.STATIC, 1000, 0.4, new Vector(0, 0)).register();
        }

        @Override
        public void loop() {
            //if (pol!=null) pol.runUpdate();
        }
    }

}
