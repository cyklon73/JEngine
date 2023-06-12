package de.cyklon.jengine.gameobject.component;

import de.cyklon.jengine.gameobject.AbstractGameObject;
import de.cyklon.jengine.gameobject.GameObject;

public abstract class ComponentBase implements ObjectComponent {

    protected GameObject gameObject;

    protected ComponentBase() {
        this.gameObject = null;
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public final void register(AbstractGameObject abstractGameObject) {
        this.gameObject = abstractGameObject;
    }
}
