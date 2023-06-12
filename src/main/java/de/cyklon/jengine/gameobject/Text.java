package de.cyklon.jengine.gameobject;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.math.Size;
import de.cyklon.jengine.math.Vector;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class Text extends AbstractGameObject {

    private static JEngine engine;

    private String text;
    private Color color, shadowColor, strikethroughColor, underlineColor;
    private Font font;
    private Alignment alignment;

    public Text() {
        this("");
    }

    public Text(String text) {
        this(text, 0, 0);
    }

    public Text(String text, double x, double y) {
        this(text, x, y, 0, 0);
    }

    public Text(String text, double x, double y, double width, double height) {
        super(x, y, 0, 0);
        this.getPosition().add(x, y);
        this.getState().getSize().add(width, height);
        this.text = text;
        this.color = Color.BLACK;
        this.shadowColor = new Color(0, 0, 0, 0);
        this.strikethroughColor = new Color(0, 0, 0, 0);
        this.underlineColor = new Color(0, 0, 0, 0);
        this.font = null;
        this.alignment = Alignment.LEFT;
    }

    public static void engine(JEngine engine) {
        if (engine==null) throw new RuntimeException("engine cannot be null!");
        Text.engine = engine;
    }


    @Override
    public void create() {

    }

    @Override
    public void destroy() {

    }

    @Override
    protected void update() {
        engine.setFont(font);
        engine.setRotation(getState().getPitch());
        engine.setRotationPoint(100, 100);

        Vector position = getPosition().clone();
        Size size = getState().getSize().clone();
        position.add(switch (alignment) {
            case LEFT -> 0;
            case CENTERED -> (size.getWidth()/2f)-(stringWidth()/2f);
            case RIGHT -> getState().getSize().getWidth()-stringWidth();
        }, 0);
        int fontSize = font.getSize();

        float factor = fontSize/16f;
        engine.setRenderColor(shadowColor);
        engine.drawString(text, (float) position.getX()+factor, (float) position.getY()+factor);

        engine.setRenderColor(color);
        engine.drawString(text, (float) position.getX(), (float) position.getY());

        int start = (int) (position.getX());
        int end = (int) (position.getX()+stringWidth());
        engine.setRenderColor(underlineColor);
        engine.drawRect(start, (int) (position.getY()+4), end-start, fontSize/10, true);
        engine.setRenderColor(strikethroughColor);
        engine.drawRect(start, (int) (position.getY()-fontSize/2.4f), end-start, fontSize/10, true);
    }

    public int stringWidth() {
        return engine.getFontMetrics(font==null ? engine.getFont() : font).stringWidth(text);
    }

    public static enum Alignment {
        LEFT,
        CENTERED,
        RIGHT
    }
}
