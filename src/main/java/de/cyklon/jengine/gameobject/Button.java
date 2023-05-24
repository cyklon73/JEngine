package de.cyklon.jengine.gameobject;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.gameobject.AbstractGameObject;
import de.cyklon.jengine.input.Keyboard;
import de.cyklon.jengine.input.Mouse;
import de.cyklon.jengine.manager.GraphicsManager;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Button extends AbstractGameObject {

    private final ActionListener onClickListener;
    private final GraphicsManager graphicsManager;

    public Button(int x, int y, int width, int height, ActionListener onClickListener) {
        super(x, y, width, height);
        this.onClickListener = onClickListener;
        this.graphicsManager = JEngine.getEngine().getGraphicsManager();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void update() {
        graphicsManager.setColor(isMouseOver() ? Color.RED : Color.GREEN);
        graphicsManager.getShapeRenderer().drawRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), true);
        if (Mouse.isClicked(Mouse.Button.LEFT) && isMouseOver()) onClickListener.actionPerformed(null);
    }
}
