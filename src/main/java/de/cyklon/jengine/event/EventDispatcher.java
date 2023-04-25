package de.cyklon.jengine.event;


import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.engine.Engine;
import de.cyklon.jengine.manager.EventManager;

import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class EventDispatcher implements MouseListener, MouseWheelListener, MouseMotionListener, KeyListener {

    private final Engine engine;
    private final EventManager eventManager;
    private final Class<EventMouse> EVENT_MOUSE = EventMouse.class;
    private final Class<EventKeyboard> EVENT_KEYBOARD = EventKeyboard.class;



    public EventDispatcher(Engine engine) {
        this.engine = engine;
        this.eventManager = engine.getEventManager();
    }

    private synchronized <T> void dispatchEvent(Class<T> eventClass, Consumer<T> consumer) {
        for (T e : eventManager.getEvents(eventClass)) consumer.accept(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        dispatchEvent(EVENT_MOUSE, (t) -> t.mouseClicked(e));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        dispatchEvent(EVENT_MOUSE, (t) -> t.mousePressed(e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dispatchEvent(EVENT_MOUSE, (t) -> t.mouseReleased(e));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        dispatchEvent(EVENT_MOUSE, (t) -> t.mouseEntered(e));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        dispatchEvent(EVENT_MOUSE, (t) -> t.mouseExited(e));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dispatchEvent(EVENT_MOUSE, (t) -> t.mouseDragged(e));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        dispatchEvent(EVENT_MOUSE, (t) -> t.mouseMoved(e));
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        dispatchEvent(EVENT_MOUSE, (t) -> t.mouseWheelMoved(e));
    }



    @Override
    public void keyTyped(KeyEvent e) {
        dispatchEvent(EVENT_KEYBOARD, (t) -> t.keyTyped(e));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        dispatchEvent(EVENT_KEYBOARD, (t) -> t.keyPressed(e));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        dispatchEvent(EVENT_KEYBOARD, (t) -> t.keyReleased(e));
    }
}
