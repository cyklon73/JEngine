package de.cyklon.jengine.gameobject;

import de.cyklon.jengine.math.Vector;

public final class ObjectHandler {


    private final AbstractGameObject abstractGameObject;
    private IObjectController controller;


    public ObjectHandler(AbstractGameObject abstractGameObject) {
        this.abstractGameObject = abstractGameObject;
        this.controller = abstractGameObject;
    }

    public void setController(IObjectController controller) {
        this.controller = controller;
    }

    public void triggerCollision(Vector collidingPos) {
        controller.onCollisionEnter(new CollisionInitializer(abstractGameObject, null));
    }

    private record CollisionInitializer(GameObject gameObject, GameObject collided) implements Collision {
        @Override
        public GameObject getCollidedObject() {
            return collided;
        }

        @Override
        public Vector getRelativVelocity() {
            return subtract(gameObject.getVelocity(), collided.getVelocity());
        }

        @Override
        public double getRelativVelocityAsDouble() {
            Vector relativ = getRelativVelocity();
            return Math.sqrt(Math.pow(relativ.getX(), 2) + Math.pow(relativ.getY(), 2));
        }


        private Vector subtract(Vector v1, Vector v2) {
            return new Vector(subtract(v1.getX(), v2.getX()), subtract(v1.getY(), v2.getY()));
        }

        private double subtract(double d1, double d2) {
            return Math.max(d1, d2) - Math.min(d1, d2);
        }
    }

}
