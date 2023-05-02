package de.cyklon.jengine.render;

import de.cyklon.jengine.math.Vector;

public interface IShapeRenderer {

    void drawRect(int x, int y, int width, int height, boolean filled);
    void drawLine(int x1, int y1, int x2, int y2);
    void drawOval(int x, int y, int width, int height, boolean filled);
    void drawPolygon(int[] xPoints, int[] yPoints, int nPoints, boolean filled);
    void drawPolyline(int[] xPoints, int[] yPoints, int nPoints, boolean filled);
    void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle, boolean filled);
    void drawRect(Vector vec, int width, int height, boolean filled);
    void drawLine(Vector vec1, Vector vec2);
    void drawOval(Vector vec, int width, int height, boolean filled);
    void drawPolygon(Vector[] points, int nPoints, boolean filled);
    void drawPolyline(Vector[] points, int nPoints, boolean filled);
    void drawArc(Vector vec, int width, int height, int startAngle, int arcAngle, boolean filled);

}
