package de.cyklon.jengine.security;

import de.cyklon.jengine.util.Random;

public class Encryption {

    public static byte[] encryptByteArray(byte[] data) {
        return encryptByteArray(data, Random.instance().randByte());
    }

    public static byte[] encryptByteArray(byte[] data, byte seed) {
        Random random = new Random(seed);
        byte[] result = new byte[data.length+1];
        result[0] = seed;
        for (int i = 1; i < result.length; i++) {
            result[i] = (byte) (data[i-1]+random.randByte());
        }
        return result;
    }

    public static byte[] decryptByteArray(byte[] data) {
        Random random = new Random(data[0]);
        byte[] result = new byte[data.length-1];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (data[i+1]-random.randByte());
        }
        return result;
    }


}
