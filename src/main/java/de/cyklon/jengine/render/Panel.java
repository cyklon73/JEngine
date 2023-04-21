package de.cyklon.jengine.render;

import de.cyklon.jengine.JEngine;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private final JEngine engine;

    public Panel(JEngine engine) {
        super();
        this.engine = engine;
    }

    @Override
    protected void paintComponent(Graphics g) {
        engine.setGraphics((Graphics2D) g);
    }
}
