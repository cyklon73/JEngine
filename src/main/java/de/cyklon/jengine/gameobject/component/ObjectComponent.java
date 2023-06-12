package de.cyklon.jengine.gameobject.component;

import de.cyklon.jengine.gameobject.AbstractGameObject;
import de.cyklon.jengine.gameobject.GameObject;

public interface ObjectComponent {

    void onUpdate();

    void register(AbstractGameObject abstractGameObject);

}
