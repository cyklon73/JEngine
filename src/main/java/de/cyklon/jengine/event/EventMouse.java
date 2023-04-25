package de.cyklon.jengine.event;

import java.awt.event.MouseWheelEvent;

public interface EventMouse extends Event {

    default void mouseClicked(java.awt.event.MouseEvent e) {

    }

    default void mousePressed(java.awt.event.MouseEvent e) {

    }


    default void mouseReleased(java.awt.event.MouseEvent e) {

    }


    default void mouseEntered(java.awt.event.MouseEvent e) {

    }


    default void mouseExited(java.awt.event.MouseEvent e) {

    }


    default void mouseDragged(java.awt.event.MouseEvent e) {

    }


    default void mouseMoved(java.awt.event.MouseEvent e) {

    }


    default void mouseWheelMoved(MouseWheelEvent e) {

    }

}
