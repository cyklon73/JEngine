package de.cyklon.jengine.input;

import de.cyklon.jengine.math.Vector;

import java.awt.*;

public class Mouse {

    private static Cursor cursor;

    public Mouse(Cursor cursor) {
        if (cursor==null) throw new RuntimeException("engine cannot be null");
        Mouse.cursor = cursor;
    }

    public static Cursor getCursor() {
        return cursor;
    }

}
