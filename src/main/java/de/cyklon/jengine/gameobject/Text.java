package de.cyklon.jengine.gameobject;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.font.FontRenderer;
import de.cyklon.jengine.math.Vector;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class Text extends AbstractGameObject {

    private static JEngine engine;

    private final FontRenderer fontRenderer;
    private String text;
    private Color color;
    private Font font;

    public Text() {
        this("");
    }

    public Text(String text) {
        super();
        this.fontRenderer = new FontRenderer(engine);
        this.text = text;
        this.color = Color.BLACK;
        this.font = null;
    }

    public static void engine(JEngine engine) {
        if (engine==null) throw new RuntimeException("engine cannot be null!");
        Text.engine = engine;
    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    protected void update() {
        fontRenderer.setColor(color);
        fontRenderer.setFont(font);
        fontRenderer.setRotation(getState().getPitch());
        engine.setRotationPoint(100, 100);
        JEngine.getEngine().getGraphicsManager().getShapeRenderer().drawLine(new Vector(0, 0), getPosition());
        fontRenderer.drawString(text, getPosition());
    }
}
