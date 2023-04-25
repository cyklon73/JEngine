package de.cyklon.jengine.engine;

import de.cyklon.jengine.event.Event;
import de.cyklon.jengine.audio.AudioManager;
import de.cyklon.jengine.manager.EventManager;
import de.cyklon.jengine.manager.GraphicsManager;
import de.cyklon.jengine.render.Canvas;
import de.cyklon.jengine.resource.IResourceManager;
import org.apache.logging.log4j.Logger;

public interface Engine {

    /**
     * Gets the graphics manager
     *
     * @return the graphics manager
     */
    GraphicsManager getGraphicsManager();

    /**
     * Gets the audio manager
     * @return the graphics manager
     */
    AudioManager getAudioManger();

    /**
     * Gets the time elapsed since the last frame update in seconds.
     *
     * @return the time elapsed in seconds
     */
    double getDeltaTime();

    /**
     * Gets the logger object from Log4J
     *
     * @return the logger
     */
    Logger getLogger();

    /**
     * Registers an event. this is needed, if you do not register your event`s, it will not work
     *
     * @param event the event to register
     */
    void registerEvent(Event event);

    /**
     * set the canvas to render
     * @param canvas
     */
    void setCanvas(Canvas canvas);

    /**
     * @return the current canvas
     */
    Canvas getCanvas();

    /**
     * @return the ResourceManager to manage your resources
     */
    IResourceManager getResourceManager();

    /**
     * @return the EventManager to register events
     */
    EventManager getEventManager();

}
