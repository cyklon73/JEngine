package de.cyklon.jengine.engine;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.audio.AudioPlayer;
import de.cyklon.jengine.event.Event;
import de.cyklon.jengine.audio.AudioManager;
import de.cyklon.jengine.manager.BaseManager;
import de.cyklon.jengine.manager.EventManager;
import de.cyklon.jengine.manager.GraphicsManager;
import de.cyklon.jengine.render.Canvas;
import de.cyklon.jengine.render.Renderer;
import de.cyklon.jengine.resource.IResourceManager;
import org.apache.logging.log4j.Logger;

public class BaseEngine implements Engine {

    private final JEngine engine;

    public BaseEngine(JEngine jEngine) {
        BaseManager.setup(jEngine);
        Renderer.setup(jEngine);
        this.engine = jEngine;
    }

    @Override
    public GraphicsManager getGraphicsManager() {
        return new BaseManager.IGraphicsManager();
    }

    @Override
    public AudioManager getAudioManger() {
        return new AudioPlayer(engine);
    }

    @Override
    public double getDeltaTime() {
        return engine.getDeltaTime();
    }

    @Override
    public Logger getLogger() {
        return engine.getLogger();
    }

    @Override
    public void registerEvent(Event event) {
        engine.registerEvent(event);
    }

    @Override
    public void setCanvas(Canvas canvas) {
        engine.setCanvas(canvas);
    }

    @Override
    public Canvas getCanvas() {
        return engine.getCanvas();
    }

    @Override
    public IResourceManager getResourceManager() {
        return engine.getResourceManager();
    }

    @Override
    public EventManager getEventManager() {
        return new BaseManager.IEventManager();
    }

}
