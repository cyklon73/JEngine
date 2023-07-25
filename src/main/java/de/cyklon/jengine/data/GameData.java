package de.cyklon.jengine.data;

import java.io.Serializable;

public class GameData<T extends Serializable> {

    private String setID;
    private final String id;
    private T value;

    public GameData(String id) {
        this(id, null);
    }

    public GameData(String id, T value) {
        if (!id.matches("[a-z._$]+")) throw new IllegalArgumentException("Invalid ID format. The ID must be lowercase, contain no spaces, no numbers and only the following special characters are allowed: . _ $");
        this.setID = null;
        this.id = id;
        this.value = value;
    }

    public void set(String id) {
        this.setID = id;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public String getRawID() {
        return id;
    }

    public String getID() {
        return (setID!=null ? setID + "." : "") + getRawID();
    }

    @Override
    public String toString() {
        return String.format("%s {%s}", getID(), getValue().toString());
    }
}
