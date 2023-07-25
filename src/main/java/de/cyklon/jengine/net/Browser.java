package de.cyklon.jengine.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Browser {

    public static void main(String[] args) {
        try {
            Process process = Runtime.getRuntime().exec(
                    "REG QUERY HKEY_LOCAL_MACHINE\\SOFTWARE\\Clients\\StartMenuInternet");

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("REG_SZ")) {
                    System.out.println(line.substring(line.lastIndexOf("\\") + 1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
