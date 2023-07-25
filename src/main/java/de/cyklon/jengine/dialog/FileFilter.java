package de.cyklon.jengine.dialog;

import java.util.HashSet;
import java.util.Set;

public class FileFilter {

    private final Set<Entry> entries;

    public FileFilter() {
        this.entries = new HashSet<>();
    }

    public void addEntry(String name, String suffix) {
        addEntry(new Entry(name, suffix));
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    public Set<Entry> getEntries() {
        return entries;
    }

    public record Entry(String name, String suffix) {

        @Override
        public String suffix() {
            String s = suffix.replace("*", "");
            if (s.startsWith(".")) s = s.substring(1, s.length() - 1);
            return s;
        }
    }
}
