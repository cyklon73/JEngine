package de.cyklon.jengine.gameobject;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.gameobject.component.ObjectComponent;
import de.cyklon.jengine.input.Mouse;
import de.cyklon.jengine.math.Size;
import de.cyklon.jengine.math.Vector;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.*;

public abstract class AbstractGameObject implements GameObject, IObjectController, Serializable {

    private static JEngine engine;

    private final UUID internalID;
    private final Vec position;
    private final Vector velocity;
    private final Map<UUID, AttachdObject> attached;
    private GameObject attachedBase;
    private Layer layer;
    private final State state;
    private final long created;
    private final List<AbstractGameObject> localGameObjects;
    private final ObjectHandler handler;
    private final Map<Class<? extends ObjectComponent>, ObjectComponent> components;

    protected AbstractGameObject() {
        this(0, 0, 0, 0);
    }

    protected AbstractGameObject(Vector position, Size size) {
        this(position.getX(), position.getY(), size.getWidth(), size.getHeight());
    }

    protected AbstractGameObject(double x, double y, double width, double height) {
        this.internalID = UUID.randomUUID();
        this.position = new Vec(x, y, () -> getAttached().forEach((obj) -> obj.getPosition().copy(getPosition())));
        this.velocity = new Vector();
        this.attached = new HashMap<>();
        this.attachedBase = null;
        this.layer = Layer.DEFAULT;
        this.state = new State(width, height, () -> getAttached().forEach((obj) -> obj.getState().setSize(getState().getSize())), () -> getAttached().forEach((obj) -> obj.getState().setPitch(getState().getPitch())));
        this.created = System.currentTimeMillis();
        this.localGameObjects = new ArrayList<>();
        this.handler = new ObjectHandler(this);
        this.components = new HashMap<>();
    }

    public abstract void create();
    public abstract void destroy();
    protected abstract void update();

    public void runUpdate() {
        update();
        localGameObjects.forEach(AbstractGameObject::runUpdate);
    }

    public static void engine(JEngine engine) {
        if (engine==null) throw new RuntimeException("engine cannot be null!");
        AbstractGameObject.engine = engine;
        Text.engine(engine);
    }

    private void attach(GameObject gameObject, Boolean pitch, Boolean x, Boolean y) {
        if (gameObject.getInternalID().equals(getInternalID())) throw new IllegalArgumentException("you cannot attach a gameObject to it self");
        if (attached.containsKey(gameObject.getInternalID())) {
            AttachdObject attachdObject = attached.get(gameObject.getInternalID());
            if (pitch!=null) attachdObject.setPitch(pitch);
            if (x!=null) attachdObject.setX(x);
            if (y!=null) attachdObject.setY(y);
        } else {
            if (pitch==null) pitch = true;
            if (x==null) x = true;
            if (y==null) y = true;
            attached.put(gameObject.getInternalID(), new AttachdObject(gameObject, pitch, x, y));
            ((AbstractGameObject)gameObject).attachedBase = this;
        }
    }

    private void strictDetach(UUID internalID) {
        if (isAttached(internalID)) ((AbstractGameObject) attached.remove(internalID).getGameObject()).attachedBase = null;
    }

    protected void registerLocal(GameObject gameObject) {
        if (gameObject instanceof AbstractGameObject abstractGameObject) {
            localGameObjects.add(abstractGameObject);
            abstractGameObject.create();
        }
    }

    protected void unregisterLocal(GameObject gameObject) {
        unregisterLocal(gameObject.getInternalID());
    }

    protected void unregisterLocal(UUID internalID) {
        for (int i = 0; i < localGameObjects.size(); i++) {
            AbstractGameObject abstractGameObject = localGameObjects.get(i);
            if (abstractGameObject.getInternalID().equals(internalID)) {
                abstractGameObject.destroy();
                localGameObjects.remove(i);
            }
        }
    }

    @Override
    public void register() {
        engine.registerGameObject(this);
    }

    @Override
    public UUID getInternalID() {
        return internalID;
    }

    @Override
    public Vector getPosition() {
        return position;
    }

    @Override
    public double getX() {
        return getPosition().getX();
    }

    @Override
    public double getY() {
        return getPosition().getY();
    }

    @Override
    public Vector getVelocity() {
        return velocity;
    }

    @Override
    public Collection<GameObject> getAttached() {
        Collection<GameObject> gameObjects = new ArrayList<>();
        attached.forEach((k, v) -> gameObjects.add(v.getGameObject()));
        return gameObjects;
    }

    @Override
    public Layer getLayer() {
        return layer;
    }

    @Override
    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    @Override
    public boolean isAttached() {
        return getAttachedBase()!=null;
    }

    @Override
    public GameObject getAttachedBase() {
        return attachedBase;
    }

    @Override
    public boolean isAttached(GameObject gameObject) {
        return isAttached(gameObject.getInternalID());
    }

    @Override
    public boolean isAttached(UUID internalID) {
        return attached.containsKey(internalID);
    }

    @Override
    public void attach(GameObject gameObject) {
        attach(gameObject, null, null, null);
    }

    @Override
    public void attach(GameObject gameObject, boolean pitch) {
        attach(gameObject, pitch, null, null);
    }

    @Override
    public void attach(GameObject gameObject, boolean x, boolean y) {
        attach(gameObject, null, x, y);
    }

    @Override
    public void attach(GameObject gameObject, boolean pitch, boolean x, boolean y) {
        attach(gameObject, (Boolean) pitch, (Boolean) x, (Boolean) y);
    }

    @Override
    public void detach(GameObject gameObject) {
        strictDetach(gameObject.getInternalID());
    }

    @Override
    public void detach(UUID internalID) {
        strictDetach(internalID);
    }

    @Override
    public void interpolate(Size size) {

    }

    @Override
    public void interpolate(float pitch) {
        interpolate(pitch, false);
    }

    @Override
    public void interpolate(float pitch, boolean inverted) {

    }

    @Override
    public void interpolate(Vector vec) {

    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public double getWidth() {
        return getState().getSize().getWidth();
    }

    @Override
    public double getHeight() {
        return getState().getSize().getHeight();
    }

    @Override
    public long getTimeCreated() {
        return created;
    }

    @Override
    public void setController(ObjectController controller) {
        this.handler.setController(controller);
    }

    @Override
    public void addComponent(ObjectComponent component) {
        components.put(component.getClass(), component);
    }

    @Override
    public Collection<ObjectComponent> getComponents() {
        return components.values();
    }

    @Override
    public boolean isComponentPresent(Class<? extends ObjectComponent> clazz) {
        return components.containsKey(clazz);
    }


    //utility methods

    protected boolean isMouseOver() {
        Vector pos = Mouse.getCursor().getCursorPositionRelative();
        return pos.getX() > getX() && pos.getX() < getX()+getWidth() && pos.getY() > getY() && pos.getY() < getY()+getHeight();
    }


    //Controller

    @Override
    public void onCollisionEnter(Collision collision) {

    }

    @Override
    public void onCollisionStay(Collision collision) {

    }

    @Override
    public void onCollisionExit(Collision collision) {

    }

    private static class AttachdObject {

        private final GameObject gameObject;
        private boolean pitch, x, y;

        AttachdObject(GameObject gameObject, boolean pitch, boolean x, boolean y) {
            this.gameObject = gameObject;
            this.pitch = pitch;
            this.x = x;
            this.y = y;
        }

        public GameObject getGameObject() {
            return gameObject;
        }

        public boolean isPitch() {
            return pitch;
        }

        public void setPitch(boolean pitch) {
            this.pitch = pitch;
        }

        public boolean isX() {
            return x;
        }

        public void setX(boolean x) {
            this.x = x;
        }

        public boolean isY() {
            return y;
        }

        public void setY(boolean y) {
            this.y = y;
        }
    }

    private static class Vec extends Vector {

        private final Runnable changeEvent;

        public Vec(Runnable changeEvent) {
            this.changeEvent = changeEvent;
        }

        public Vec(Point2D point, Runnable changeEvent) {
            super(point);
            this.changeEvent = changeEvent;
        }

        public Vec(double x, double y, Runnable changeEvent) {
            super(x, y);
            this.changeEvent = changeEvent;
        }

        @Override
        public Vector add(double x, double y) {
            changeEvent.run();
            return super.add(x, y);
        }

        @Override
        public Vector subtract(double x, double y) {
            changeEvent.run();
            return super.subtract(x, y);
        }

        @Override
        public Vector times(double x, double y) {
            changeEvent.run();
            return super.times(x, y);
        }

        @Override
        public Vector divide(double x, double y) {
            changeEvent.run();
            return super.divide(x, y);
        }

        @Override
        public Vector normalize() {
            changeEvent.run();
            return super.normalize();
        }

        @Override
        public Vector copy(Vector v) {
            changeEvent.run();
            return super.copy(v);
        }

        @Override
        public Vector setX(int x) {
            changeEvent.run();
            return super.setX(x);
        }

        @Override
        public Vector setX(float x) {
            changeEvent.run();
            return super.setX(x);
        }

        @Override
        public Vector setX(double x) {
            changeEvent.run();
            return super.setX(x);
        }

        @Override
        public Vector setY(int y) {
            changeEvent.run();
            return super.setY(y);
        }

        @Override
        public Vector setY(float y) {
            changeEvent.run();
            return super.setY(y);
        }

        @Override
        public Vector setY(double y) {
            changeEvent.run();
            return super.setY(y);
        }
    }
}
