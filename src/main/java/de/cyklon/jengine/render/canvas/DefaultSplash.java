package de.cyklon.jengine.render.canvas;

import de.cyklon.jengine.manager.GraphicsManager;
import de.cyklon.jengine.render.JEngineIcon;

import java.awt.*;

public final class DefaultSplash extends SplashCanvas {

    private GraphicsManager gm;

    @Override
    public void init() {
        this.gm = getEngine().getGraphicsManager();
    }

    @Override
    public JEngineIcon initJEngineIcon() {
        return new JEngineIcon() {
            @Override
            public int size() {
                return 40;
            }

            @Override
            public int x() {
                return (int) (getEngine().getGraphicsManager().getScreenSize().getWidth()/2-width()/2);
            }

            @Override
            public int y() {
                return (int) (getEngine().getGraphicsManager().getScreenSize().getHeight()/2-height()/2);
            }

            @Override
            public Style style() {
                return Style.DARK;
            }

            @Override
            public Variant variant() {
                return Variant.DEFAULT;
            }
        };
    }

    @Override
    public void loop() {
        Dimension d = getEngine().getGraphicsManager().getScreenSize();
        gm.setColor(Color.DARK_GRAY);
        gm.getShapeRenderer().drawRect(0, 0, (int) d.getWidth(), (int) d.getHeight(), true);
    }
}
