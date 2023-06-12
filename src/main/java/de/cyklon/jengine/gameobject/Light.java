package de.cyklon.jengine.gameobject;

import de.cyklon.jengine.math.Size;
import de.cyklon.jengine.math.Vector;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Light extends AbstractGameObject {

    public static final int STATIC = 0;
    public static final int DYNAMIC = 1;

    private int type;
    private int radius;
    private double brightness;

    public Light(int type, int radius, double brightness, Vector position) {
        super(position, new Size());
        this.type = type;
        this.radius = radius;
        this.brightness = Math.max(0, Math.min(1, brightness));
    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {

    }

    @Override
    protected void update() {

    }

}
