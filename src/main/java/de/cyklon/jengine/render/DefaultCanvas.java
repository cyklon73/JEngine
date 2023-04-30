package de.cyklon.jengine.render;

import java.awt.*;

public class DefaultCanvas extends Canvas {
    @Override
    public void loop() {
        Dimension d = getEngine().getGraphicsManager().getWindowManager().getDimension();
        getEngine().getGraphicsManager().getFontRenderer().drawCenteredString(new Font("Bahnschrift", Font.BOLD, 40), "Set your Canvas", d.getWidth()/2, d.getHeight()/2 - 25);
        getEngine().getGraphicsManager().getFontRenderer().drawCenteredString(new Font("Bahnschrift", Font.BOLD, 40), "to display your Game", d.getWidth()/2, d.getHeight()/2 + 25);
    }
}
