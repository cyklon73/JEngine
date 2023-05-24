package de.cyklon.jengine.math;

import java.util.Collection;
import java.util.Random;

public class Maths {

    public static double distance(double d1, double d2) {
        return Math.max(d1, d2) - Math.min(d1, d2);
    }

    public static double random() {
        return new Random().nextDouble();
    }

    public static double randomDouble(double min, double max) {
        return random()*distance(min, max)+min;
    }

    public static double randomDouble(double max) {
        return randomDouble(0, max);
    }

    public static float randomFloat(float min, float max) {
        return (float) (random()*distance(min, max)+min);
    }

    public static float randomFloat(float max) {
        return randomFloat(0, max);
    }

    public static long randomLong(long min, long max) {
        return (long) (random()*distance(min, max)+min);
    }

    public static long randomLong(long max) {
        return randomLong(0, max);
    }

    public static int randomInt(int min, int max) {
        return (int) (random()*(distance(min, max)+1)+min);
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
