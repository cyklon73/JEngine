package de.cyklon.jengine.font;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.math.Vector;
import de.cyklon.jengine.render.IFontRenderer;

import java.awt.*;

public class FontRenderer implements IFontRenderer {

    private final JEngine engine;

    public FontRenderer(JEngine engine) {
        this.engine = engine;
    }


    @Override
    public void setFont(Font font) {
        engine.setFont(font);
    }

    @Override
    public void drawString(String str, double x, double y) {
        engine.drawString(str, (float) x, (float) y);
    }

    @Override
    public void drawString(String str, Vector vec) {
        drawString(str, vec.getX(), vec.getY());
    }

    @Override
    public void drawString(Font font, String str, double x, double y) {
        Font f = engine.getFont();
        setFont(font);
        drawString(str, x, y);
        setFont(f);
    }

    @Override
    public void drawString(Font font, String str, Vector vec) {
        Font f = engine.getFont();
        setFont(font);
        drawString(str, vec);
        setFont(f);
    }

    @Override
    public void drawStringWithShadow(String str, double x, double y) {
        Color normal = engine.getRenderColor();
        Color shadow = (normal.getRed() < 25 && normal.getGreen() < 25 && normal.getBlue() < 25) ? normal.brighter().brighter() : normal.darker();
        engine.setRenderColor(shadow);
        int size = engine.getFont().getSize();
        float factor = 16f;
        drawString(str, x+size/factor, y+size/factor);
        engine.setRenderColor(normal);
        drawString(str, x, y);
    }

    @Override
    public void drawStringWithShadow(String str, Vector vec) {
        drawStringWithShadow(str, vec.getX(), vec.getY());
    }

    @Override
    public void drawStringWithShadow(Font font, String str, double x, double y) {
        Font f = engine.getFont();
        setFont(font);
        drawStringWithShadow(str, x, y);
        setFont(f);
    }

    @Override
    public void drawStringWithShadow(Font font, String str, Vector vec) {
        Font f = engine.getFont();
        setFont(font);
        drawStringWithShadow(str, vec);
        setFont(f);
    }

    @Override
    public void drawCenteredString(String str, double x, double y) {
        drawString(str, x-(stringWidth(str)/2f), y);
    }

    @Override
    public void drawCenteredString(String str, Vector vec) {
        drawCenteredString(str, vec.getX(), vec.getY());
    }

    @Override
    public void drawCenteredString(Font font, String str, double x, double y) {
        drawString(font, str, x-(stringWidth(font, str)/2f), y);
    }

    @Override
    public void drawCenteredString(Font font, String str, Vector vec) {
        drawCenteredString(font, str, vec.getX(), vec.getY());
    }

    @Override
    public void drawCenteredStringWithShadow(String str, double x, double y) {
        drawStringWithShadow(str, x-(stringWidth(str)/2f), y);
    }

    @Override
    public void drawCenteredStringWithShadow(String str, Vector vec) {
        drawCenteredStringWithShadow(str, vec.getX(), vec.getY());
    }

    @Override
    public void drawCenteredStringWithShadow(Font font, String str, double x, double y) {
        drawStringWithShadow(font, str, x-(stringWidth(font, str)/2f), y);
    }

    @Override
    public void drawCenteredStringWithShadow(Font font, String str, Vector vec) {
        drawCenteredStringWithShadow(font, str, vec.getX(), vec.getY());
    }

    @Override
    public int stringWidth(String str) {
        return engine.getFontMetrics().stringWidth(str);
    }

    @Override
    public int stringWidth(Font font, String str) {
        return engine.getFontMetrics(font).stringWidth(str);
    }

    public void setColor(Color color) {
        engine.setRenderColor(color);
    }

    public void setRotation(double angle) {
        engine.setRotation(angle);
    }
}
