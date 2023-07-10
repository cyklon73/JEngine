package de.cyklon.jengine.data;

import java.io.Serializable;
import java.util.*;

public class DataSet {

    private final static List<String> sets = new ArrayList<>();

    private final String id;
    private final Map<String, GameData<? extends Serializable>> dataMap;


    public DataSet(String id) {
        if (!id.matches("[a-z._$]+")) throw new IllegalArgumentException("Invalid ID format. The ID must be lowercase, contain no spaces, no numbers and only the following special characters are allowed: . _ $");
        this.id = id;
        this.dataMap = new HashMap<>();
    }

    public String getID() {
        return id;
    }

    public void addData(GameData<? extends Serializable> data) {
        data.set(this.id);
        dataMap.put(data.getRawID(), data);
    }

    public Collection<GameData<?>> getData() {
        return dataMap.values();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (GameData<?> gameData : getData()) {
            sb.append(gameData.toString()).append(", ");
        }
        sb = new StringBuilder(sb.substring(0, sb.length()-2));
        sb.append("]");
        return sb.toString();
    }
}
