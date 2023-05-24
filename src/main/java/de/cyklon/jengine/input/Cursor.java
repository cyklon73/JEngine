package de.cyklon.jengine.input;

import de.cyklon.jengine.math.Vector;

public interface Cursor {

    Vector getCursorPosition();

    Vector getCursorPositionRelative();

    void setHidden(boolean hidden);

    boolean isHidden();

    void setFixed(boolean fixed);

    boolean isFixed();

}
