package de.cyklon.jengine.render;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.engine.Engine;
import org.apache.logging.log4j.Logger;

public abstract class Canvas {

    public abstract void loop();

    protected Engine getEngine() {
        return JEngine.getEngine();
    }

    protected Logger getLogger() {
        return getEngine().getLogger();
    }

}
