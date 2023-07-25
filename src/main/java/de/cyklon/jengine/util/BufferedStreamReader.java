package de.cyklon.jengine.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BufferedStreamReader {


    private final BufferedReader reader;

    public BufferedStreamReader(InputStream in) {
        this.reader = new BufferedReader(new InputStreamReader(in));
    }

    public String read() throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(reader);
        String line;
        while ((line = br.readLine())!=null) sb.append(line).append("\n");
        return sb.length()>=1 ? sb.substring(0, sb.length()-1) : "";
    }

}
