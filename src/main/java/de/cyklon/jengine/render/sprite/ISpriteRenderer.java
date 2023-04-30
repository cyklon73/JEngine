package de.cyklon.jengine.render.sprite;

import de.cyklon.jengine.exeption.UnsupportedFileException;
import de.cyklon.jengine.resource.Resource;

import java.io.IOException;

public interface ISpriteRenderer {

    Sprite spriteFromResource(Resource resource) throws UnsupportedFileException, IOException;
    void renderSprite(Sprite sprite, int x, int y);
    void renderSprite(Resource resource, int x, int y) throws UnsupportedFileException, IOException;


}
