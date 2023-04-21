package de.cyklon.jengine.test;

import de.cyklon.jengine.event.Event;
import de.cyklon.jengine.event.WindowCloseEvent;
import de.cyklon.jengine.event.WindowMoveEvent;

public class Test {

    public static void main(String[] args) {
        Event event = Util.createEvent();
        System.out.println(isEvent(event, WindowCloseEvent.class));
    }

    private static boolean isEvent(Event event, Class<? extends Event> clazz) {
        Class<?>[] interfaces = event.getClass().getInterfaces();
        for (Class<?> i : interfaces) {
            if (Event.class.isAssignableFrom(i)) {
                return i.equals(clazz);
            }
        }
        return false;
    }

}
