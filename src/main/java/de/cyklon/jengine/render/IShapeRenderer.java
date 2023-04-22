package de.cyklon.jengine.render;

import de.cyklon.jengine.util.Vector;

public interface IShapeRenderer {

    void drawRect(int x, int y, int width, int height);
    void drawLine(int x1, int y1, int x2, int y2);
    void drawOval(int x, int y, int width, int height);
    void drawPolygon(int[] xPoints, int[] yPoints, int nPoints);
    void drawPolyline(int[] xPoints, int[] yPoints, int nPoints);
    void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle);
    void drawRect(Vector vec, int width, int height);
    void drawLine(Vector vec1, Vector vec2);
    void drawOval(Vector vec, int width, int height);
    void drawPolygon(Vector[] points, int nPoints);
    void drawPolyline(Vector[] points, int nPoints);
    void drawArc(Vector vec, int width, int height, int startAngle, int arcAngle);

}
