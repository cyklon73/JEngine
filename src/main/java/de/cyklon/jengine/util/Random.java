package de.cyklon.jengine.util;

public class Random {

    private final java.util.Random rand;

    public Random(long seed) {
        this.rand = new java.util.Random(seed);
    }

    public static Random instance() {
        return new Random(System.currentTimeMillis());
    }


    public byte[] randBytes(int size) {
        return randBytes(size, Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    public byte[] randBytes(int size, byte min, byte max) {
        byte[] result = new byte[size];
        for (int i = 0; i < result.length; i++) {
            result[i] = randByte(min, max);
        }
        return result;
    }

    public byte randByte() {
        return randByte(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    public byte randByte(byte min, byte max) {
        return (byte) randInt(min, max);
    }

    public int randInt() {
        return randInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public int randInt(int min, int max) {
        return (int) (random()*max)+min;
    }

    public double random() {
        return getRandom().nextDouble();
    }

    private java.util.Random getRandom() {
        return rand;
    }


}
