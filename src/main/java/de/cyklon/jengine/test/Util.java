package de.cyklon.jengine.test;

import de.cyklon.jengine.event.Event;
import de.cyklon.jengine.event.WindowCloseEvent;

public class Util {

    public static Event createEvent() {
        return (WindowCloseEvent) () -> {};
    }

}
