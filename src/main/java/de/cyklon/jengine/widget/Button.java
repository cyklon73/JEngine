package de.cyklon.jengine.widget;

import de.cyklon.jengine.input.Keyboard;
import de.cyklon.jengine.manager.GraphicsManager;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Button extends Widget {

    private final ActionListener onClickListener;

    public Button(int x, int y, int width, int height, ActionListener onClickListener) {
        super(x, y, width, height);
        this.onClickListener = onClickListener;
    }

    @Override
    public void render(GraphicsManager graphicsManager) {
        graphicsManager.setColor(isMouseOver() ? Color.RED : Color.GREEN);
        graphicsManager.getShapeRenderer().drawRect(x, y, width, height, true);
        if (Keyboard.isPressed(KeyEvent.VK_W) && isMouseOver()) onClickListener.actionPerformed(null);
    }
}
