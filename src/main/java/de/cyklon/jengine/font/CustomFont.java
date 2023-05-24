package de.cyklon.jengine.font;

import de.cyklon.jengine.resource.Resource;
import de.cyklon.jengine.util.FileUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public class CustomFont {

    public static Font getFont(Resource resource, int style, float size) throws IOException, FontFormatException {
        if (resource==null) return null;
        return getFont(resource).deriveFont(style, size);
    }

    public static Font getFont(Resource resource, Map<? extends AttributedCharacterIterator.Attribute, ?> attributes) throws IOException, FontFormatException {
        if (resource==null) return null;
        return getFont(resource).deriveFont(attributes);
    }

    public static Font getFont(Resource resource, int style, AffineTransform trans) throws IOException, FontFormatException {
        if (resource==null) return null;
        return getFont(resource).deriveFont(style, trans);
    }

    public static Font getFont(Resource resource, AffineTransform trans) throws IOException, FontFormatException {
        if (resource==null) return null;
        return getFont(resource).deriveFont(trans);
    }

    public static Font getFont(Resource resource, float size) throws IOException, FontFormatException {
        if (resource==null) return null;
        return getFont(resource).deriveFont(size);
    }

    public static Font getFont(Resource resource, int style) throws IOException, FontFormatException {
        if (resource==null) return null;
        return getFont(resource).deriveFont(style);
    }

    public static Font getFont(Resource resource) throws IOException, FontFormatException {
        if (resource==null) return null;
        return Font.createFont(getType(FileUtils.getSuffix(resource.getFile())), resource.getInputStream());
    }

    private static int getType(String suffix) {
        return switch (suffix) {
            case "ttf", "otf" -> Font.TRUETYPE_FONT;
            case "pfa", "pfb" -> Font.TYPE1_FONT;
            default -> 0;
        };
    }
}
