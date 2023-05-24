package de.cyklon.jengine.render;

import de.cyklon.jengine.input.Mouse;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DraggableBackground extends MouseAdapter {

    private boolean enabled;
    private Point click;

    private final Component component;

    public DraggableBackground(Component component) {
        this.component = component;
        this.enabled = false;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (enabled && !Mouse.getCursor().isFixed() && click!=null) {
            Point point = MouseInfo.getPointerInfo().getLocation();
            component.setLocation(new Point((int) (point.getX() - click.getX()), (int) (point.getY() - click.getY())));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (enabled) click = e.getPoint();
    }
}
