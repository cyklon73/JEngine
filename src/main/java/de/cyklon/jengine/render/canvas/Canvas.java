package de.cyklon.jengine.render.canvas;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.engine.Engine;
import de.cyklon.jengine.event.Event;
import org.apache.logging.log4j.Logger;

public abstract class Canvas implements Event {


    public void init() {}

    public void loop() {}

    protected Engine getEngine() {
        return JEngine.getEngine();
    }
    protected double getDeltaTime() {
        return getEngine().getDeltaTime();
    }

    protected Logger getLogger() {
        return getEngine().getLogger();
    }

}
