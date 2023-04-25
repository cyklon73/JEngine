package de.cyklon.jengine.util;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

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

    @Override
    public String toString() {
        return x + "," + y;
    }

    public Dimension toDimension() {
        return new Dimension((int) x, (int) y);
    }
    public static Vector parseVector(String s) {
        String[] c = s.split(",");
        return new Vector(Float.parseFloat(c[0]), Float.parseFloat(c[1]));
    }

    public static Vector parseVector(String s1, String s2, String s3) {
        return new Vector(Float.parseFloat(s1), Float.parseFloat(s2));
    }

    public static Vector parseVector(String[] s) {
        return new Vector(Float.parseFloat(s[0]), Float.parseFloat(s[1]));
    }

    public static Vector parseVector(Float[] s) {
        return new Vector(s[0], s[1]);
    }

    public static Vector parseVector(Integer[] s) {
        return new Vector(s[0], s[1]);
    }

    public Vector subtract(Vector vec) {
        x -= vec.x;
        y -= vec.y;
        return this;
    }

    public Vector times(double n) {
        x *= n;
        y *= n;
        return this;
    }


    public Vector cross(Vector vec) {
        return new Vector(y*vec.x-x*vec.y, y*vec.x-x*vec.y);
    }

    public double scalar(Vector vec) {
        return x*vec.x+y*vec.y+x*vec.x;
    }

    public double angle(Vector vec) {
        return Math.acos(clone().normalize().scalar(vec.clone().normalize()));
    }

    public Vector normalize() {
        double length = length();
        x /= length;
        y /= length;
        return this;
    }

    public ArrayList<Double> toArrayList() {
        ArrayList<Double> a = new ArrayList<>();
        a.add(x);
        a.add(y);
        return a;
    }

    public void add (Vector v) {
        x += v.x;
        y += v.y;
    }

    @Override
    public Vector clone () {
        return new Vector(x, y);
    }

    public Vector copy(Vector v) {
        x = v.x;
        y = v.y;
        return this;
    }

    public Vector setX(int x) {
        this.x = x;
        return this;
    }

    public Vector setX(double x) {
        this.x = (float) x;
        return this;
    }

    public Vector setX(float x) {
        this.x = x;
        return this;
    }

    public Vector setY(int y) {
        this.y = y;
        return this;
    }

    public Vector setY(double y) {
        this.y = (float) y;
        return this;
    }

    public Vector setY(float y) {
        this.y = y;
        return this;
    }

    public double length() {
        return Math.sqrt(Math.sqrt(x) + Math.sqrt(y));
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
