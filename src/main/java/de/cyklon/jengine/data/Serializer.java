package de.cyklon.jengine.data;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Serializer {

    public static void main(String[] args) throws IOException {
        GameData<?> gameData = new GameData<>("test_value", new TestClass("world"));
        DataSet set = new DataSet("test_set");
        set.addData(gameData);
        System.out.println(Arrays.toString(Serializer.serialize(set)));
    }


    public static byte[] serialize(DataSet set) throws IOException {
        if (set==null) throw new IllegalArgumentException("set cannot be null!");
        String id = set.getID();
        List<GameData<?>> dataL = set.getData().stream().toList();
        GameData<? extends Serializable>[] data = new GameData<?>[dataL.size()];
        for (int i = 0; i < data.length; i++) {
            data[i] = dataL.get(i);

        }


        byte[][][] result = new byte[data.length+1][2][0];
        result[0] = new byte[1][0];
        result[0][0] = id.getBytes();
        for (int i = 1; i < data.length; i++) {
            GameData<? extends Serializable> gameData = data[i-1];
            result[i][0] = gameData.getID().getBytes();
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(gameData.getValue());
                result[i][1] = bos.toByteArray();
            }
        }
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(result);
            return bos.toByteArray();
        }
    }

    public static DataSet deserialize(byte[] data) throws IOException, ClassNotFoundException {
        byte[][][] dataW;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data); ObjectInputStream ois = new ObjectInputStream(bis)) {
            dataW = (byte[][][]) ois.readObject();
        }
        DataSet set = new DataSet(new String(dataW[0][0]));
        for (int i = 1; i < dataW.length; i++) {
            GameData<?> gameData = new GameData<>(new String(dataW[i][0]));
            try (ByteArrayInputStream bis = new ByteArrayInputStream(dataW[i][1]); ObjectInputStream ois = new ObjectInputStream(bis)) {
                //gameData.setValue(Serializable.class.cast(ois.readObject()));
            }
        }
        return null;
    }



}
