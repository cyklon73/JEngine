package de.cyklon.jengine;

import de.cyklon.jengine.engine.Engine;
import de.cyklon.jengine.event.EventKeyboard;
import de.cyklon.jengine.manager.GraphicsManager;
import de.cyklon.jengine.manager.WindowManager;
import de.cyklon.jengine.render.Canvas;
import de.cyklon.jengine.resource.IResourceManager;
import de.cyklon.jengine.util.Vector;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;

public class TestGame {

    public static void main(String[] args) throws ClassNotFoundException, IOException, UnsupportedAudioFileException, URISyntaxException {
        JEngine.start();
        Engine engine = JEngine.getEngine();

        IResourceManager resourceManager = engine.getResourceManager();
        GraphicsManager gm = engine.getGraphicsManager();
        WindowManager wm = gm.getWindowManager();

        resourceManager.loadResource("wav", "jump.wav");
        resourceManager.loadResource("ogg", "example.ogg");
        resourceManager.loadResource("mp3", "a2.mp3");
        resourceManager.loadResource("icon", "cringe2.1.png");


        wm.setDimension(new Dimension(500, 500));
        wm.setLocation(new Vector(500, 500));

        wm.hideTitleBar(true);
        wm.setDraggableBackground(true);
        wm.setIcon(resourceManager.getResource("icon"));

        wm.setVisible(true);

        gm.setVsync(true);

        engine.getAudioManger().play(resourceManager.getResource("mp3"));



    }


    public static class MenuCanvas extends Canvas implements EventKeyboard {

        @Override
        public void init() {
            super.init();
        }

        double x = 0;
        double y = 0;
        double xToGo = 0;
        double yToGo = 0;
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar()=='w') yToGo-=50;
            else if (e.getKeyChar()=='s') yToGo+=50;

            if (e.getKeyChar()=='a') xToGo-=50;
            else if (e.getKeyChar()=='d') xToGo+=50;
        }

        @Override
        public void loop() {
            double speed = 50;
            if (xToGo>0) {
                x+=speed*getDeltaTime();
                xToGo-=speed*getDeltaTime();
            } else if (xToGo<0) {
                x-=speed*getDeltaTime();
                xToGo+=speed*getDeltaTime();
            }
            if (yToGo>0) {
                y+=speed*getDeltaTime();
                yToGo-=speed*getDeltaTime();
            } else if (yToGo<0) {
                y-=speed*getDeltaTime();
                yToGo+=speed*getDeltaTime();
            }
            getEngine().getGraphicsManager().setColor(Color.RED);
            getEngine().getGraphicsManager().getFontRenderer().drawString(new Font("Arial", Font.PLAIN, 50), "FPS: " + getEngine().getGraphicsManager().getFPS(), x, y);
        }
    }

}
