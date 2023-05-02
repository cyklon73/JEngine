package de.cyklon.jengine.math;

public class JMath {

    public static double difference(double d1, double d2) {
        return Math.max(d1, d2) - Math.min(d1, d2);
    }

}
