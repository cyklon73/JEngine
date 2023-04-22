package de.cyklon.jengine.manager;

import de.cyklon.jengine.event.Event;

import java.util.List;

public interface EventManager {

    void registerEvents(Event event);

    <T> List<T> getEvents(Class<T> eventClass);

}
