package de.cyklon.jengine.render.sprite;

import de.cyklon.jengine.resource.Resource;
import de.cyklon.jengine.util.Vector;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public interface Sprite {

    /**
     * you can completely ignore this method, its only important for intern usage
     */
    long getID();
    Resource getResource();
    Image getImage();
    int getWidth();
    int getHeight();
    Sprite setWidth(int width);
    Sprite setHeight(int height);
    void render(Vector vec);
    void render(int x, int y);


}
