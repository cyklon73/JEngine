package de.cyklon.jengine.render;

import de.cyklon.jengine.util.Vector;

import java.awt.*;

public interface IFontRenderer {

    void setFont(Font font);
    void drawString(String str, double x, double y);
    void drawString(String str, Vector vec);

    void drawString(Font font, String str, double x, double y);
    void drawString(Font font, String str, Vector vec);

    void drawStringWithShadow(String str, double x, double y);
    void drawStringWithShadow(String str, Vector vec);

    void drawStringWithShadow(Font font, String str, double x, double y);
    void drawStringWithShadow(Font font, String str, Vector vec);



    void drawCenteredString(String str, double x, double y);
    void drawCenteredString(String str, Vector vec);

    void drawCenteredString(Font font, String str, double x, double y);
    void drawCenteredString(Font font, String str, Vector vec);

    void drawCenteredStringWithShadow(String str, double x, double y);
    void drawCenteredStringWithShadow(String str, Vector vec);

    void drawCenteredStringWithShadow(Font font, String str, double x, double y);
    void drawCenteredStringWithShadow(Font font, String str, Vector vec);

    int stringWidth(String str);
    int stringWidth(Font font, String str);

}
