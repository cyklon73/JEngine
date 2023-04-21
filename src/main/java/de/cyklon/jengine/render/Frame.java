package de.cyklon.jengine.render;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    private final DraggableBackground draggableBackground;

    public Frame() throws HeadlessException {
        super();
        this.draggableBackground = new DraggableBackground(this);
        setupDraggableBackground();
    }

    public Frame(GraphicsConfiguration gc) {
        super(gc);
        this.draggableBackground = new DraggableBackground(this);
        setupDraggableBackground();
    }

    public Frame(String title) throws HeadlessException {
        super(title);
        this.draggableBackground = new DraggableBackground(this);
        setupDraggableBackground();
    }

    public Frame(String title, GraphicsConfiguration gc) {
        super(title, gc);
        this.draggableBackground = new DraggableBackground(this);
        setupDraggableBackground();
    }

    private void setupDraggableBackground() {
        this.addMouseListener(draggableBackground);
        this.addMouseMotionListener(draggableBackground);
    }

    public void setDraggableBackground(boolean draggable) {
        draggableBackground.setEnabled(draggable);
    }

    public boolean isBackgroundDraggable() {
        return draggableBackground.isEnabled();
    }

}
