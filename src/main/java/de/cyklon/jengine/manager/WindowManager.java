package de.cyklon.jengine.manager;

import de.cyklon.jengine.resource.Resource;
import de.cyklon.jengine.util.Vector;

import java.awt.*;
import java.io.IOException;

public interface WindowManager {

    /**
     * Sets the dimension of the game window.
     *
     * @param dimension the dimension to set
     */
    void setDimension(Dimension dimension);

    /**
     * Gets the current dimension of the game window.
     *
     * @return the current dimension of the game window
     */
    Dimension getDimension();

    /**
     * Sets the location of the game window on the screen.
     *
     * @param vec the vector representing the new location of the game window
     */
    void setLocation(Vector vec);

    /**
     * Gets the current location of the game window on the screen.
     *
     * @return the current location of the game window as a vector
     */
    Vector getLocation();

    /**
     * Sets whether the game window is visible or not.
     *
     * @param visible true to make the game window visible, false to hide it
     */
    void setVisible(boolean visible);

    /**
     * Checks if the game window is visible or not.
     *
     * @return true if the game window is visible, false otherwise
     */
    boolean isVisible();

    /**
     * Sets whether the game window is resizable or not.
     *
     * @param resizable true to make the game window resizable, false to make it non-resizable
     */
    void setResizable(boolean resizable);

    /**
     * Checks if the game window is resizable or not.
     *
     * @return true if the game window is resizable, false otherwise
     */
    boolean isResizable();

    /**
     * Sets whether the game window's title bar should be hidden or not.
     *
     * @param hiding true to hide the game window's title bar, false to show it
     */
    void hideTitleBar(boolean hiding);

    /**
     * Checks if the game window's title bar is hidden or not.
     *
     * @return true if the game window's title bar is hidden, false otherwise
     */
    boolean isTitleBarHidden();

    /**
     * Sets whether the game window's background can be dragged or not.
     * <p>
     * it`s recommend to enable this option if the title bar was hidden
     *
     * @param draggable true to make the game window's background draggable, false to make it non-draggable
     */
    void setDraggableBackground(boolean draggable);

    /**
     * Checks if the game window's background can be dragged or not.
     *
     * @return true if the game window's background is draggable, false otherwise
     */
    boolean isBackgroundDraggable();

    /**
     * set the game icon
     * @param resource the resource file for the icon
     */
    void setIcon(Resource resource) throws IOException;
}
