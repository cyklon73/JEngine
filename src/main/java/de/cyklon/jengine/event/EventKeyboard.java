package de.cyklon.jengine.event;

import java.awt.event.KeyEvent;

public interface EventKeyboard extends Event {

    default void keyTyped(KeyEvent e) {

    }

    default void keyPressed(KeyEvent e) {

    }

    default void keyReleased(KeyEvent e) {

    }

}
