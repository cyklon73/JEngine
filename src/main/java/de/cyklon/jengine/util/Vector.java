package de.cyklon.jengine.util;

import java.awt.geom.Point2D;

public class Vector {

    /**
     * the x and y coordinates of the vector
     */
    private double x, y;

    public Vector() {
        this(0, 0);
    }

    public Vector(Point2D point) {
        this(point.getX(), point.getY());
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x coord
     */
    public double getX() {
        return x;
    }

    /**
     * @return the y coord
     */
    public double getY() {
        return y;
    }

    /**
     * converts the vector to a awt Point
     * @return the Point
     */
    public java.awt.Point toPoint() {
        return new VecPoint(this);
    }

    private static class VecPoint extends java.awt.Point {

        public VecPoint(Vector vector) {
            this(vector.getX(), vector.getY());
        }

        public VecPoint(double x, double y) {
            setLocation(x, y);
        }

    }


}
