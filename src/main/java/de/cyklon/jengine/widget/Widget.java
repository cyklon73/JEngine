package de.cyklon.jengine.widget;

import de.cyklon.jengine.input.Mouse;
import de.cyklon.jengine.manager.GraphicsManager;
import de.cyklon.jengine.math.Vector;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Widget {

    protected int x, y, width, height;

    public Widget(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public abstract void render(GraphicsManager graphicsManager);


    protected boolean isMouseOver() {
        Vector pos = Mouse.getCursor().getCursorPositionRelative();
        return pos.getX() > getX() && pos.getX() < getX()+getWidth() && pos.getY() > getY() && pos.getY() < getY()+getHeight();
    }

}
