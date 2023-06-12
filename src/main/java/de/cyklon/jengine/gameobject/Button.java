package de.cyklon.jengine.gameobject;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.input.Mouse;
import de.cyklon.jengine.manager.GraphicsManager;

import java.awt.*;
import java.awt.event.ActionListener;

public class Button extends AbstractGameObject {

    private final ActionListener onClickListener;
    private final GraphicsManager graphicsManager;
    private final Text text;

    public Button(String text, int x, int y, int width, int height, ActionListener onClickListener) {
        super(x, y, width, height);
        this.onClickListener = onClickListener;
        this.graphicsManager = JEngine.getEngine().getGraphicsManager();
        this.text = new Text(text, x-5, y+3, width, height);
        this.text.setAlignment(Text.Alignment.CENTERED);
        this.text.setFont(new Font("Arial", Font.BOLD, (int) (Math.min(width, height)/1.5f)));
        registerLocal(this.text);
    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void update() {
        graphicsManager.setColor(isMouseOver() ? Color.RED : Color.GREEN);
        graphicsManager.getShapeRenderer().drawRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), true);
        if (Mouse.isClicked(Mouse.Button.LEFT) && isMouseOver()) onClickListener.actionPerformed(null);
    }
}
