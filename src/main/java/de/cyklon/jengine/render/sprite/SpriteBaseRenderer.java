package de.cyklon.jengine.render.sprite;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.exception.UnsupportedFileException;
import de.cyklon.jengine.resource.Resource;
import de.cyklon.jengine.util.FileUtils;
import de.cyklon.jengine.util.Vector;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SpriteBaseRenderer implements ISpriteRenderer {

    private static JEngine engine;

    private static final Map<Long, SpriteData> data = new HashMap<>();
    private static long ID = 0;

    private SpriteBaseRenderer() {

    }

    public static void setup(JEngine engine) {
        SpriteBaseRenderer.engine = engine;
    }

    public static ISpriteRenderer getSpriteRenderer() {
        return new SpriteBaseRenderer();
    }

    @Override
    public Sprite spriteFromResource(Resource resource) throws IOException, UnsupportedFileException {
        return initSprite(resource);
    }

    @Override
    public void renderSprite(Sprite sprite, int x, int y) {
        engine.drawImage(sprite.getImage(), x, y, sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void renderSprite(Resource resource, int x, int y) throws IOException, UnsupportedFileException {
        renderSprite(spriteFromResource(resource), x, y);
    }

    private void initData(long id) {
        data.put(id, new SpriteData());
    }

    private Sprite initSprite(final Resource resource) throws IOException, UnsupportedFileException {
        if (!isFormatSupported(resource.getBytes())) throw new UnsupportedFileException("File format " + FileUtils.getSuffix(resource.getFile()) + " is not Supported! List of supported formats: " + Arrays.toString(ImageIO.getReaderFileSuffixes()));
        final long id_ = ID;
        initData(id_);
        ID++;
        final ImageIcon icon = new ImageIcon(resource.getBytes(), "");
        final Image img = icon.getImage();
        return new Sprite() {

            @Override
            public long getID() {
                return id_;
            }

            @Override
            public Resource getResource() {
                return resource;
            }

            @Override
            public Image getImage() {
                return img;
            }

            @Override
            public int getWidth() {
                Dimension size = data.get(getID()).getSize();
                return size==null ? icon.getIconWidth() : (int) size.getWidth();
            }

            @Override
            public int getHeight() {
                Dimension size = data.get(getID()).getSize();
                return size==null ? icon.getIconHeight() : (int) size.getHeight();
            }

            @Override
            public Sprite setWidth(int width) {
                data.get(getID()).setSize(new Dimension(width, getHeight()));
                return this;
            }

            @Override
            public Sprite setHeight(int height) {
                data.get(getID()).setSize(new Dimension(getWidth(), height));
                return this;
            }

            @Override
            public void render(Vector vec) {
                render((int) vec.getX(), (int) vec.getY());
            }

            @Override
            public void render(int x, int y) {
                SpriteBaseRenderer.this.renderSprite(this, x, y);
            }
        };
    }

    private boolean isFormatSupported(byte[] imageData) {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(imageData)) {
            ImageIO.read(stream);
            return true;
        } catch (IOException ignored) {}
        return false;
    }

    @Getter
    @Setter
    private static class SpriteData {
        private Dimension size;

        public SpriteData() {
            this.size = null;
        }
    }
}
