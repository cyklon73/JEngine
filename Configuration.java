package de.cyklon.jengine;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Configuration {

    private final Map<String, Object> configMap;

    public Configuration() {
        this.configMap = new HashMap<>();
    }

    public void configure(String property, Object value) {
        configMap.put(property, value);
    }

    public Object get(String property) {
        return configMap.get(property);
    }

    public Integer getInteger(String property) {
        return (Integer) get(property);
    }

    public Double getDouble(String property) {
        return (Double) get(property);
    }

    public Float getFloat(String property) {
        return (Float) get(property);
    }

    public Long getLong(String property) {
        return (Long) get(property);
    }

    public Short getShort(String property) {
        return (Short) get(property);
    }

    public Byte getByte(String property) {
        return (Byte) get(property);
    }

    public Character getCharacter(String property) {
        return (Character) get(property);
    }
    public String getString(String property) {
        return (String) get(property);
    }
    public Boolean getBoolean(String property) {
        return (Boolean) get(property);
    }


    public Object[] getArray(String property) {
        return (Object[]) configMap.get(property);
    }

    public Integer[] getIntegerArray(String property) {
        return (Integer[]) get(property);
    }

    public Double[] getDoubleArray(String property) {
        return (Double[]) get(property);
    }

    public Float[] getFloatArray(String property) {
        return (Float[]) get(property);
    }

    public Long[]getLongArray(String property) {
        return (Long[]) get(property);
    }

    public Short[] getShortArray(String property) {
        return (Short[]) get(property);
    }

    public Byte[] getByteArray(String property) {
        return (Byte[]) get(property);
    }

    public Character[] getCharacterArray(String property) {
        return (Character[]) get(property);
    }
    public String[] getStringArray(String property) {
        return (String[]) get(property);
    }
    public Boolean[] getBooleanArray(String property) {
        return (Boolean[]) get(property);
    }

    public void mergeDefault(Configuration def) {
        def.forEach(this.configMap::putIfAbsent);
    }

    public void forEach(BiConsumer<String, Object> consumer) {
        this.configMap.forEach(consumer);
    }
}
