package de.cyklon.jengine.exeption;

import java.io.File;

public class UnsupportedFileException extends Exception {

    public UnsupportedFileException() {
        super();
    }

    public UnsupportedFileException(String message) {
        super(message);
    }

    public UnsupportedFileException(String message, File file) {
        super(String.format("%s - FileType: %s", message, file.getName().split("\\.")[file.getName().split("\\.").length-1]));
    }
}
