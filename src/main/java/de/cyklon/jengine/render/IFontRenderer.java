package de.cyklon.jengine.render;

import de.cyklon.jengine.util.Vector;

import java.awt.*;

public interface IFontRenderer {

    void setFont(Font font);
    void drawString(String msg, double x, double y);
    void drawString(String msg, Vector vec);

    void drawString(Font font, String msg, double x, double y);
    void drawString(Font font, String msg, Vector vec);

    void drawStringWithShadow(String msg, double x, double y);
    void drawStringWithShadow(String msg, Vector vec);

    void drawStringWithShadow(Font font, String msg, double x, double y);
    void drawStringWithShadow(Font font, String msg, Vector vec);

}
