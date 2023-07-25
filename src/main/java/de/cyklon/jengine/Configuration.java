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

    public <T> T get(String property, Class<T> type) {
        return type.cast(get(property));
    }

    public void mergeDefault(Configuration def) {
        def.forEach(this.configMap::putIfAbsent);
    }

    public void forEach(BiConsumer<String, Object> consumer) {
        this.configMap.forEach(consumer);
    }
}
