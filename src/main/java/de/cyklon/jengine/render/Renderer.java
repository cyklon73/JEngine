package de.cyklon.jengine.render;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.manager.BaseManager;
import de.cyklon.jengine.util.Vector;

import java.awt.*;

public class Renderer {

    private static JEngine engine;

    public static void setup(JEngine engine) {
        Renderer.engine = engine;
    }

    public static class FontRenderer implements IFontRenderer {

        @Override
        public void setFont(Font font) {
            engine.setFont(font);
        }

        @Override
        public void drawString(String msg, double x, double y) {
            engine.drawString(msg, (float) x, (float) y);
        }

        @Override
        public void drawString(String msg, Vector vec) {
            drawString(msg, vec.getX(), vec.getY());
        }

        @Override
        public void drawString(Font font, String msg, double x, double y) {
            Font f = engine.getFont();
            setFont(font);
            drawString(msg, x, y);
            setFont(f);
        }

        @Override
        public void drawString(Font font, String msg, Vector vec) {
            Font f = engine.getFont();
            setFont(font);
            drawString(msg, vec);
            setFont(f);
        }

        @Override
        public void drawStringWithShadow(String msg, double x, double y) {
            Color normal = engine.getRenderColor();
            engine.setRenderColor(normal.darker());
            int size = engine.getFont().getSize();
            float factor = 12f;
            drawString(msg, x+size/factor, y+size/factor);
            engine.setRenderColor(normal);
            drawString(msg, x, y);
        }

        @Override
        public void drawStringWithShadow(String msg, Vector vec) {
            drawStringWithShadow(msg, vec.getX(), vec.getY());
        }

        @Override
        public void drawStringWithShadow(Font font, String msg, double x, double y) {
            Font f = engine.getFont();
            setFont(font);
            drawStringWithShadow(msg, x, y);
            setFont(f);
        }

        @Override
        public void drawStringWithShadow(Font font, String msg, Vector vec) {
            Font f = engine.getFont();
            setFont(font);
            drawStringWithShadow(msg, vec);
            setFont(f);
        }
    }


}
