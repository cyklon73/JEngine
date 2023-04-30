package de.cyklon.jengine.util;

import java.io.File;

public class FileUtils {

    public static String getSuffix(File file) {
        String[] args = file.getName().split("\\.");
        return args[args.length-1];
    }

}
