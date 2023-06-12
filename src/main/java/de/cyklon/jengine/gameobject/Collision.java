package de.cyklon.jengine.gameobject;

import de.cyklon.jengine.math.Vector;

public interface Collision {

    GameObject gameObject();
    GameObject getCollidedObject();
    Vector getRelativVelocity();
    double getRelativVelocityAsDouble();
}
