package de.cyklon.jengine.render.canvas;

import de.cyklon.jengine.render.JEngineIcon;

public abstract class SplashCanvas extends Canvas {

    protected double getLoadingProgress() {
        return 0.5d;
    }
    public abstract JEngineIcon initJEngineIcon();

}
