package de.cyklon.jengine.audio;

/**
 * this enum contains all supported Audio File Types
 */
public enum AudioType {
    MP3("mp3"),
    OGG("ogg"),
    WAV("wav"),
    AU("au"),
    AIFF("aif"),
    AIFC("aifc"),
    SND("snd");



    private final String extension;

    AudioType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public static AudioType getFromExtension(String extension) {
        for (AudioType format : values()) {
            if (format.getExtension().equalsIgnoreCase(extension)) return format;
        }
        return null;
    }
}
