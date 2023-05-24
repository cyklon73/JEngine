package de.cyklon.jengine.input;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.util.Pair;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Keyboard {

    private static JEngine engine;
    private static final Map<Integer, Long> keyMap = new HashMap<>();
    private static final Deque<Pair<Integer, Integer>> keyQueue = new ArrayDeque<>();

    public Keyboard(JEngine engine) {
        if (engine==null) throw new RuntimeException("Engine cannot be null");
        Keyboard.engine = engine;

    }

    public void shutdown() {

    }

    public static boolean isPressed(int keyCode) {
        return keyMap.containsKey(keyCode) && keyMap.get(keyCode)>=0;
    }

    public static boolean isClicked(int keyCode) {
        return keyMap.containsKey(keyCode) && keyMap.get(keyCode)==engine.getTickTime();
    }

    public static boolean isReleased(int keyCode) {
        return keyMap.containsKey(keyCode) && (keyMap.get(keyCode)*-1)==engine.getTickTime();
    }

    public ActionListener tick() {
        return e -> {
            if (e.getActionCommand().startsWith("new")) {
                while (!keyQueue.isEmpty()) {
                    Pair<Integer, Integer> pair = keyQueue.remove();
                    keyMap.put(pair.getFirst(), engine.getTickTime()*pair.getSecond());
                }
            }
        };
    }

    public AbstractAction pressed() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand()==null) {
                    return;
                };
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(e.getActionCommand().strip().charAt(0));
                if (!isPressed(keyCode)) keyQueue.add(new Pair<>(keyCode, 1));
            }
        };
    }

    public AbstractAction released() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand()==null) {
                    return;
                };
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(e.getActionCommand().strip().charAt(0));
                keyQueue.add(new Pair<>(keyCode, -1));
            }
        };
    }

    /*@Override
    public void keyTyped(KeyEvent e) {
        //engine.getLogger().debug("typed: " + e.getKeyCode());
        keyMap.put(e.getKeyCode(), engine.getTickTime());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        engine.getLogger().debug("released: " + e.getKeyCode());
        keyMap.put(e.getKeyCode(), engine.getTickTime()*-1);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        engine.getLogger().debug("pressed: " + e.getKeyCode());
    }*/

    public static enum Key {

        W(0x57);

        private final int code;

        Key(int code) {
            this.code = code;
        }

        public int getKeyCode() {
            return code;
        }

        public boolean isPressed() {
            return Keyboard.isPressed(getKeyCode());
        }

        public boolean isClicked() {
            return Keyboard.isClicked(getKeyCode());
        }

        public boolean isReleased() {
            return Keyboard.isReleased(getKeyCode());
        }
    }
}
