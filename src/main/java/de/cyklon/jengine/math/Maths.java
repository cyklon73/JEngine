package de.cyklon.jengine.math;

import java.util.Collection;
import java.util.Random;

public class Maths {

    public static double range(double d1, double d2) {
        return Math.max(d1, d2) - Math.min(d1, d2);
    }

    public static double square(double d) {
        return d*d;
    }

    /**
     * use instead {@link Vector#relativ(Vector)}
     * @param v1 first Vector
     * @param v2 second Vector
     * @return the Relative Position from the second Vector to the first Vector
     * @see Vector
     */
    @Deprecated
    public static Vector relative(Vector v1, Vector v2) {
        return new Vector(v1.getX()-v2.getX(), v1.getY()-v2.getY());
    }

    /**
     * use instead {@link Vector#distance(Vector)}
     * @param v1 first Vector
     * @param v2 second Vector
     * @return the distance between the first and the second Vector
     * @see Vector
     */
    @Deprecated
    public static double distance(Vector v1, Vector v2) {
        Vector v = relative(v1, v2);
        return StrictMath.sqrt(square(v.getX())+square(v.getY()));
    }

    public static double random() {
        return new Random().nextDouble();
    }

    public static double randomDouble(double min, double max) {
        return random()* range(min, max)+min;
    }

    public static double randomDouble(double max) {
        return randomDouble(0, max);
    }

    public static float randomFloat(float min, float max) {
        return (float) (random()* range(min, max)+min);
    }

    public static float randomFloat(float max) {
        return randomFloat(0, max);
    }

    public static long randomLong(long min, long max) {
        return (long) (random()* range(min, max)+min);
    }

    public static long randomLong(long max) {
        return randomLong(0, max);
    }

    public static int randomInt(int min, int max) {
        return (int) (random()*(range(min, max)+1)+min);
    }

    public static int randomInt(int max) {
        return randomInt(0, max);
    }

    public static boolean randomBool() {
        return randomInt(1) != 0;
    }

    public static <T> T randomChoice(T[] array) {
        return array[randomInt(array.length-1)];
    }

    public static <T> T randomChoice(Collection<T> collection) {
        return randomChoice(collection.toArray((T[]) new Object[collection.size()]));
    }

}
