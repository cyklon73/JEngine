package de.cyklon.jengine.dialog;

import java.io.File;

public class FileDialog {

    public static File open(String title) {
        return open(title, null);
    }

    public static native File open(String title, FileFilter filter);

    public static native File save();

}
