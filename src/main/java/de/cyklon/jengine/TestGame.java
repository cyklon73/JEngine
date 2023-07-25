package de.cyklon.jengine;

import de.cyklon.jengine.audio.AudioTask;
import de.cyklon.jengine.engine.Engine;
import de.cyklon.jengine.event.EventKeyboard;
import de.cyklon.jengine.exception.UnsupportedFileException;
import de.cyklon.jengine.font.CustomFont;
import de.cyklon.jengine.gameobject.Light;
import de.cyklon.jengine.gameobject.Text;
import de.cyklon.jengine.input.Cursor;
import de.cyklon.jengine.input.Keyboard;
import de.cyklon.jengine.input.Mouse;
import de.cyklon.jengine.manager.GraphicsManager;
import de.cyklon.jengine.manager.WindowManager;
import de.cyklon.jengine.render.canvas.Canvas;
import de.cyklon.jengine.render.sprite.Sprite;
import de.cyklon.jengine.resource.IResourceManager;
import de.cyklon.jengine.resource.Resource;
import de.cyklon.jengine.math.Vector;
import de.cyklon.jengine.gameobject.Button;
import de.cyklon.jengine.util.Constants;

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

        resourceManager.loadResource("wav", "starjump.wav");
        resourceManager.loadResource("ogg", "example.ogg");
        resourceManager.loadResource("mp3", "HDBeenDope - BYRD (Official Music Video).mp3");
        resourceManager.loadResource("icon", "cringe2.1.png");

        resourceManager.loadResource("font.mc", "font/Minecraft.ttf");
        resourceManager.loadResource("font.blox", "font/Blox.ttf");

        resourceManager.loadResource("png", "ArtusPB.png");
        resourceManager.loadResource("gif", "AC.gif");


        wm.setDimension(new Dimension(500, 500));
        wm.setLocation(new Vector(500, 500));

        wm.hideTitleBar(false);
        wm.setDraggableBackground(true);
        //wm.setIcon(resourceManager.getResource("icon"));
        wm.setDimension(new Dimension(500, 500));
        wm.setMinSize(wm.getDimension());
        wm.setMaxSize(new Dimension(wm.getDimension().width, Integer.MAX_VALUE));

        engine.setCanvas(new TestCanvas());

        wm.setVisible(true);

        gm.setVsync(true);
        //gm.setMaxFPS(-1);

        AudioTask task = engine.getAudioManger().play(resourceManager.getResource("mp3").getAudio());
        engine.getAudioManger().stop(task);


    }

    public static class TestCanvas extends Canvas {

        Text text;

        Button button;

        @Override
        public void init() {
            text = new Text();
            text.setFont(new Font("Arial", Font.BOLD, 120));
            text.getPosition().add(50, 150);
            text.setUnderlineColor(Color.RED);
            text.setShadowColor(Color.GREEN);
            text.getState().getSize().set(800, 100);
            text.register();

            button = new Button("Button", 10, 10, 50, 20, (e) -> System.out.println("Button abc pressed!"));
            button.register();

        }

        @Override
        public void loop() {
            text.setText("FPS: " + getEngine().getGraphicsManager().getFPS());
            text.setAlignment(Text.Alignment.CENTERED);
            if (Mouse.isPressed(Mouse.Button.LEFT)) text.getPosition().add(10*getDeltaTime(), 0);
            if (Mouse.isPressed(Mouse.Button.RIGHT)) text.getPosition().add(-10*getDeltaTime(), 0);
            if (Keyboard.isPressed(KeyEvent.VK_W)) text.getPosition().add(0,10*getDeltaTime());
            if (Keyboard.isPressed(KeyEvent.VK_S)) text.getPosition().add(0,-10*getDeltaTime());
        }
    }


    public static class MenuCanvas extends Canvas implements EventKeyboard {

        long start = 0;
        Sprite sprite;
        Resource mcFont, bloxFont;
        Button button;
        @Override
        public void init() {
            start = System.currentTimeMillis();
            try {
                sprite = getEngine().getGraphicsManager().getSpriteRenderer().spriteFromResource(getEngine().getResourceManager().getResource("gif"));
                sprite.setWidth((int) getEngine().getGraphicsManager().getWindowManager().getDimension().getWidth());
                sprite.setHeight((int) getEngine().getGraphicsManager().getWindowManager().getDimension().getHeight());
            } catch (UnsupportedFileException | IOException e) {
                e.printStackTrace();
            }
            mcFont = getEngine().getResourceManager().getResource("font.mc");
            bloxFont = getEngine().getResourceManager().getResource("font.blox");
            button = new Button("Button", 20, 20, 100, 30, (e) -> System.out.println("Clicked"));
        }

        double x = 0;
        double y = 100;

        double speed = 100;
        @Override
        public void loop() {
            Cursor cursor = Mouse.getCursor();
            if (Keyboard.isClicked(KeyEvent.VK_H)) {
                cursor.setFixed(true);
                cursor.setHidden(true);
            }
            if (Keyboard.isClicked(KeyEvent.VK_U)) {
                cursor.setFixed(false);
                cursor.setHidden(false);
            }

            if (Keyboard.isPressed(KeyEvent.VK_W)) y-=speed*getDeltaTime();
            if (Keyboard.isPressed(KeyEvent.VK_S)) y+=speed*getDeltaTime();
            if (Keyboard.isPressed(KeyEvent.VK_A)) x-=speed*getDeltaTime();
            if (Keyboard.isPressed(KeyEvent.VK_D)) x+=speed*getDeltaTime();

            long ms = System.currentTimeMillis()-start;

            getEngine().getGraphicsManager().setColor(Color.RED);
            try {
                getEngine().getGraphicsManager().getFontRenderer().drawString(CustomFont.getFont(bloxFont, Font.PLAIN, 50), "FPS: " + getEngine().getGraphicsManager().getFPS(), x, y);
                getEngine().getGraphicsManager().getFontRenderer().drawString(CustomFont.getFont(bloxFont, Font.PLAIN, 50), "tt: " + JEngine.gtt(), x, y+50);
                getEngine().getGraphicsManager().getFontRenderer().drawString(CustomFont.getFont(mcFont, Font.PLAIN, 50), String.format("%sh %sm %ss", ((ms/(1000*60*60))%24), (ms/(1000*60)%60), (ms/1000%60)), x+100, y+100);
            } catch (IOException | FontFormatException e) {
                e.printStackTrace();
            }
        }
    }

}
