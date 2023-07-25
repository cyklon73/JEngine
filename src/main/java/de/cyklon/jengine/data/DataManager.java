package de.cyklon.jengine.data;

import de.cyklon.jengine.security.Encryption;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {

    private static final List<String> fileNames = new ArrayList<>();
    private static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public static void main(String[] args) {
        for (int i = 0; i<999999999; i++) {
            System.out.println(getNextFileName());
        }
    }

    private final File baseFile = new File("a.bin");
    private final Map<String, DataSet> map;


    public DataManager() {
        this.map = new HashMap<>();
    }


    public void save(DataSet set) {
        map.put(set.getID(), set);
    }

    public DataSet load(String id) {
        return map.get(id);
    }

    private static String getNextFileName() {
        String nextCombination = "";
        char[] lastCombination = (fileNames.isEmpty() ? "" : fileNames.get(fileNames.size()-1)).toCharArray();

        if (lastCombination.length==0) {
            nextCombination = "a";
        } else {
            boolean carry = true;

            for (int i = lastCombination.length-1; i>=0; i--) {
                char currentChar = lastCombination[i];
                if (carry) {
                    if (currentChar=='z') lastCombination[i] = 'a';
                    else {
                        lastCombination[i]++;
                        carry = false;
                    }
                }
            }

            nextCombination =  (carry ? "a" : "") + new String(lastCombination);
        }
        fileNames.add(nextCombination);
        return nextCombination;
    }

    private void put() {

    }

    private void save() {

    }

    private void saveBase() {
        byte[] data = "test hallo welt!!!!!".getBytes();
        try (FileOutputStream fos = new FileOutputStream(baseFile)) {
            fos.write(Encryption.encryptByteArray(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBase() {

    }


}
