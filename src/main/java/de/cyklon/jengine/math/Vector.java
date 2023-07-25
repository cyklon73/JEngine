package de.cyklon.jengine.math;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Vector implements Cloneable, Serializable {



    /**
     * the x and y coordinates of the vector
     */
    private double x, y;

    /**
     * Default constructor - x/y initialised to zero.
     */
    public Vector() {
        this(0, 0);
    }

    /**
     * Point to Vector Constructor.
     *
     * @param point Sets the x and y value.
     */
    public Vector(Point2D point) {
        this(point.getX(), point.getY());
    }

    /**
     * Constructor.
     *
     * @param x Sets x value.
     * @param y Sets y value.
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy constructor.
     *
     * @param vec Vector to copy.
     */
    public Vector(Vector vec) {
        this.x = vec.getX();
        this.y = vec.getY();
    }

    /**
     * Constructs a normalised direction vector.
     *
     * @param direction Direction in radians.
     */
    public Vector(double direction) {
        this.x = Math.cos(direction);
        this.y = Math.sin(direction);
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

    public Vector add(Vector v) {
        return add(v.getX(), v.getY());
    }

    public Vector add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector subtract(Vector v) {
        return subtract(v.getX(), v.getY());
    }

    public Vector subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector times(Vector v) {
        return subtract(v.getX(), v.getY());
    }

    public Vector times(double n) {
        return times(n, n);
    }

    public Vector times(double x, double y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vector divide(Vector v) {
        return divide(v.getX(), v.getY());
    }

    public Vector divide(double n) {
        return divide(n, n);
    }

    public Vector divide(double x, double y) {
        this.x /= x;
        this.y /= y;
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

    /**
     * Finds cross product between two vectors.
     *
     * @param v Other vector to apply cross product to
     * @return double
     */
    public double crossProduct(Vector v) {
        return this.x * v.y - this.y * v.x;
    }

    public Vector crossProduct(double a) {
        return this.normal().scalar(a);
    }

    public Vector scalar(double a) {
        return new Vector(x * a, y * a);
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

    @Override
    public Vector clone () {
        return new Vector(this);
    }

    public Vector copy(Vector v) {
        x = v.x;
        y = v.y;
        return this;
    }

    public Vector set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector set(int x, int y) {
        this.x = x;
        this.y = y;
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
        return StrictMath.sqrt(Maths.square(x) + Maths.square(y));
    }

    /**
     * Negates the current instance vector and return this.
     *
     * @return Return the negative form of the instance vector.
     */
    public Vector negative() {
        this.x = -x;
        this.y = -y;
        return this;
    }

    /**
     * Negates the current instance vector and return this.
     *
     * @return Returns a new negative vector of the current instance vector.
     */
    public Vector negativeVec() {
        return new Vector(-x, -y);
    }

    /**
     * Static method for any cross product, same as
     *
     * @param s double.
     * @param a Vectors2D.
     * @return Cross product scalar result.
     */
    public static Vector cross(Vector a, double s) {
        return new Vector(s * a.y, -s * a.x);
    }

    /**
     * Finds the cross product of a scalar and a vector. Produces a scalar in 2D.
     *
     * @param s double.
     * @param a Vectors2D.
     * @return Cross product scalar result.
     */
    public static Vector cross(double s, Vector a) {
        return new Vector(-s * a.y, s * a.x);
    }

    /**
     * Finds dotproduct between two vectors.
     *
     * @param v Other vector to apply dotproduct to.
     * @return double
     */
    public double dotProduct(Vector v) {
        return v.getX() * this.x + v.getY() * this.y;
    }

    /**
     * Finds the normalised version of a vector and returns a new vector of it.
     *
     * @return A normalized vector of the current instance vector.
     */
    public Vector getNormalized() {
        double d = StrictMath.sqrt(x * x + y * y);

        if (d == 0) {
            d = 1;
        }
        return new Vector(x / d, y / d);
    }

    /**
     * Checks to see if a vector has valid values set for x and y.
     *
     * @return boolean value whether a vector is valid or not.
     */
    public final boolean isValid() {
        return !Double.isNaN(x) && !Double.isInfinite(x) && !Double.isNaN(y) && !Double.isInfinite(y);
    }

    /**
     * Checks to see if a vector is set to (0,0).
     *
     * @return boolean value whether the vector is set to (0,0).
     */
    public boolean isZero() {
        return Math.abs(this.x) == 0 && Math.abs(this.y) == 0;
    }


    /**
     * Adds a vector and the current instance vector together and returns a new vector of them added together.
     *
     * @param v Vector to add.
     * @return Returns a new Vectors2D of the sum of the addition of the two vectors.
     */
    public Vector addi(Vector v) {
        return new Vector(x + v.x, y + v.y);
    }

    /**
     * Generates a normal of a vector. Normal facing to the right clock wise 90 degrees.
     *
     * @return A normal of the current instance vector.
     */
    public Vector normal() {
        return new Vector(-y, x);
    }

    /**
     * @param v the other Vector
     * @return the Relative Position from the other Vector to this Vector
     * @see Maths
     */
    public Vector relativ(Vector v) {
        return Maths.relative(this, v);
    }

    /**
     * @param v the other Vector
     * @return the distance between this and the other Vector
     * @see Maths
     */
    public double distance(Vector v) {
        return Maths.distance(this, v);
    }

    /**
     * Generates an array of length n with zero initialised vectors.
     *
     * @param n Length of array.
     * @return A Vectors2D array of zero initialised vectors.
     */
    public static Vector[] createArray(int n) {
        Vector[] array = new Vector[n];
        for (Vector v : array) {
            v = new Vector();
        }
        return array;
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
