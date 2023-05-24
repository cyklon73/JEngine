package de.cyklon.jengine.gameobject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Layer {
    private static final String TEMP = String.format("__temp_%s_", UUID.randomUUID());
    private static final Map<String, Layer> layers = new HashMap<>();

    public static final Layer DEFAULT = new Layer("DEFAULT", 0);

    private final String name;
    private final int layer;

    public Layer(int layer) {
        this(TEMP, layer);
    }

    public Layer(String name, int layer) {
        this.name = name;
        this.layer = layer;
        if (!name.equals(TEMP)) layers.put(name, this);
    }

    public static Layer getLayer(String name) {
        return layers.get(name);
    }

    public String getName() {
        return name.equals(TEMP) ? null : name;
    }

    public int getLayer() {
        return layer;
    }
}
