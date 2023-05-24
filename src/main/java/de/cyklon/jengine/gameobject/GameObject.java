package de.cyklon.jengine.gameobject;

import de.cyklon.jengine.math.Size;
import de.cyklon.jengine.math.Vector;

import java.util.Collection;
import java.util.UUID;

public interface GameObject {

    /**
     * register the gameObject to your game
     */
    public void register();

    /**
     * @return a unique id for every gameObject
     * @see UUID
     */
    public UUID getInternalID();

    /**
     * the returned Vector can be edited
     * @return the current position of the gameObject
     * @see Vector
     */
    public Vector getPosition();

    /**
     * the returned Vector can be edited
     * @return the current velocity of the gameObject as a Vector
     * @see Vector
     */
    public Vector getVelocity();

    /**
     * @return a collection of all gameObjects attached to this gameObject
     * @see Collection
     */
    public Collection<GameObject> getAttached();

    /**
     * the layer defines which gameObject should be rendered in which order.
     * <p>
     * a gameObject with a lower layer will be rendered under a gameObject with a higher layer.
     * @return the current layer of the gameObject
     */
    public Layer getLayer();

    /**
     * set the Layer for this gameObject
     * the layer defines which gameObject should be rendered in which order.
     * <p>
     * a gameObject with a lower layer will be rendered under a gameObject with a higher layer.
     * @param layer the new layer to set
     */
    public void setLayer(Layer layer);

    /**
     * @return true if this gameObject is attached to some other gameObject
     */
    public boolean isAttached();

    /**
     * @return null if the gameObject is not attached to some otherObject else the gameObject to which this gameObject was attached
     */
    public GameObject getAttachedBase();

    /**
     * @param gameObject the gameObject to check
     * @return true if the gameObject is attached to this gameObject
     */
    public boolean isAttached(GameObject gameObject);
    /**
     * @param internalID the internalID of the gameObject to check
     * @return true if the gameObject is attached to this gameObject
     */
    public boolean isAttached(UUID internalID);

    /**
     * attach a gameObject to this gameObject with all attach aspects enabled
     * @param gameObject the gameObject to attach
     */
    public void attach(GameObject gameObject);

    public void attach(GameObject gameObject, boolean pitch);
    public void attach(GameObject gameObject, boolean x, boolean y);
    public void attach(GameObject gameObject, boolean pitch, boolean x, boolean y);
    public void detach(GameObject gameObject);
    public void detach(UUID internalID);
    public void interpolate(Size size);
    public void interpolate(float pitch);
    public void interpolate(float pitch, boolean inverted);
    public void interpolate(Vector vec);
    public State getState();
    public long getTimeCreated();


    public static class State {
        private final Runnable rotationEvent;
        private final Siz size;
        private double pitch;

        public State(double width, double height, Runnable sizeEvent, Runnable rotationEvent) {
            this.size = new Siz(width, height, sizeEvent);
            this.pitch = 0;
            this.rotationEvent = rotationEvent;
        }

        public Size getSize() {
            return size;
        }

        public void setSize(Size size) {
            setSize(size.getWidth(), size.getHeight());
        }

        public void setSize(double width, double height) {
            this.size.set(width, height);
        }

        public double getPitch() {
            return pitch;
        }

        public void setPitch(double pitch) {
            rotationEvent.run();
            this.pitch = pitch;
        }

        private static class Siz extends Size {

            private final Runnable changeEvent;

            public Siz(double width, double height, Runnable changeEvent) {
                super(width, height);
                this.changeEvent = changeEvent;
            }

            @Override
            public void setWidth(double width) {
                changeEvent.run();
                super.setWidth(width);
            }

            @Override
            public void setHeight(double height) {
                changeEvent.run();
                super.setHeight(height);
            }

            @Override
            public void set(double width, double height) {
                changeEvent.run();
                super.set(width, height);
            }

            @Override
            public void add(double width, double height) {
                changeEvent.run();
                super.add(width, height);
            }

            @Override
            public void subtract(double width, double height) {
                changeEvent.run();
                super.subtract(width, height);
            }

            @Override
            public void times(double width, double height) {
                changeEvent.run();
                super.times(width, height);
            }

            @Override
            public void divide(double width, double height) {
                changeEvent.run();
                super.divide(width, height);
            }
        }
    }

}
