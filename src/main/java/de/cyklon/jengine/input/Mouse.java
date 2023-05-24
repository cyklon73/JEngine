package de.cyklon.jengine.input;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.util.Pair;

import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Mouse {

    private static JEngine engine;
    private static Cursor cursor;
    private static final Map<Integer, Long> keyMap = new HashMap<>();
    private static final Deque<Pair<Integer, Integer>> keyQueue = new ArrayDeque<>();

    public Mouse(JEngine engine, Cursor cursor) {
        if (engine==null) throw new RuntimeException("engine cannot be null");
        if (cursor==null) throw new RuntimeException("cursor cannot be null");
        Mouse.engine = engine;
        Mouse.cursor = cursor;
    }

    public static Cursor getCursor() {
        return cursor;
    }

    public static boolean isPressed(int button) {
        return keyMap.containsKey(button) && keyMap.get(button)>=0;
    }

    public static boolean isClicked(int button) {
        return keyMap.containsKey(button) && keyMap.get(button)==engine.getTickTime();
    }

    public static boolean isReleased(int button) {
        return keyMap.containsKey(button) && (keyMap.get(button)*-1)==engine.getTickTime();
    }

    public ActionListener tick() {
        return e -> {
            if (e.getActionCommand().startsWith("new")) {
                while (!keyQueue.isEmpty()) {
                    Pair<Integer, Integer> pair = keyQueue.remove();
                    keyMap.put(pair.getFirst(), engine.getTickTime()*pair.getSecond());
                    System.out.println(pair.getFirst());
                }
            }
        };
    }

    public MouseInputListener inputListener() {
        return new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                keyQueue.add(new Pair<>(e.getButton(), 1));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                keyQueue.add(new Pair<>(e.getButton(), -1));
            }
        };
    }

    public static enum Button {

        LEFT(1),
        MIDDLE(2),
        RIGHT(3),
        BUTTON_4(4),
        BUTTON_5(5);

        private final int button;

        Button(int button) {
            this.button = button;
        }

        public int getButton() {
            return button;
        }

        public boolean isPressed() {
            return Mouse.isPressed(getButton());
        }

        public boolean isClicked() {
            return Mouse.isClicked(getButton());
        }

        public boolean isReleased() {
            return Mouse.isReleased(getButton());
        }
    }

}
