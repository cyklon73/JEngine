package de.cyklon.jengine.render;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.manager.BaseManager;
import de.cyklon.jengine.util.Pair;
import de.cyklon.jengine.util.Vector;

import java.awt.*;

public class Renderer {

    private static JEngine engine;

    public static void setup(JEngine engine) {
        Renderer.engine = engine;
    }

    private static Pair<int[], int[]> splitVectorArray(Vector[] vecArray) {
        int[] xPoints = new int[vecArray.length];
        int[] yPoints = new int[vecArray.length];
        for (int i = 0; i < vecArray.length; i++) {
            Vector vec = vecArray[i];
            xPoints[i] = (int) vec.getX();
            yPoints[i] = (int) vec.getY();
        }
        return new Pair<>(xPoints, yPoints);
    }

    public static class FontRenderer implements IFontRenderer {

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
    }


    public static class ShapeRenderer implements IShapeRenderer {

        @Override
        public void drawRect(int x, int y, int width, int height, boolean filled) {
            engine.drawRect(x, y, width, height, filled);
        }

        @Override
        public void drawLine(int x1, int y1, int x2, int y2) {
            engine.drawLine(x1, y1, x2, y2);
        }

        @Override
        public void drawOval(int x, int y, int width, int height, boolean filled) {
            engine.drawOval(x, y, width, height, filled);
        }

        @Override
        public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints, boolean filled) {
            engine.drawPolyline(xPoints, yPoints, nPoints, filled);
        }

        @Override
        public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints, boolean filled) {
            engine.drawPolyline(xPoints, yPoints, nPoints, filled);
        }

        @Override
        public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle, boolean filled) {
            engine.drawArc(x, y, width, height, startAngle, arcAngle, filled);
        }

        @Override
        public void drawRect(Vector vec, int width, int height, boolean filled) {
            drawRect((int) vec.getX(), (int) vec.getY(), width, height, filled);
        }

        @Override
        public void drawLine(Vector vec1, Vector vec2) {
            drawLine((int) vec1.getX(), (int) vec1.getY(), (int) vec2.getX(), (int) vec2.getY());
        }

        @Override
        public void drawOval(Vector vec, int width, int height, boolean filled) {
            drawOval((int) vec.getX(), (int) vec.getY(), width, height, filled);
        }

        @Override
        public void drawPolygon(Vector[] points, int nPoints, boolean filled) {
            Pair<int[], int[]> pair = splitVectorArray(points);
            drawPolygon(pair.getFirst(), pair.getSecond(), nPoints, filled);
        }

        @Override
        public void drawPolyline(Vector[] points, int nPoints, boolean filled) {
            Pair<int[], int[]> pair = splitVectorArray(points);
            drawPolyline(pair.getFirst(), pair.getSecond(), nPoints, filled);
        }

        @Override
        public void drawArc(Vector vec, int width, int height, int startAngle, int arcAngle, boolean filled) {
            drawArc((int) vec.getX(), (int) vec.getY(), width, height, startAngle, arcAngle, filled);
        }
    }


}
