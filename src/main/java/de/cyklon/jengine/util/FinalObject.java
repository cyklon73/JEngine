package de.cyklon.jengine.util;

public class FinalObject<T> {

    private final int limit;
    private int current;
    private T t;


    public FinalObject() {
        this(1);
    }

    public FinalObject(T t) {
        this(1, t);
    }

    public FinalObject(int limit) {
        this.limit = limit;
        this.current = 0;
        this.t = null;
    }

    public FinalObject(int limit, T t) {
        this.limit = limit;
        this.current = 0;
        set(t);
    }

    public void set(T t) {
        if (current==limit) throw new IllegalStateException("this object is final Locked. Limit is " + limit + ".");
        this.t = t;
        current++;
    }

    public T get() {
        return t;
    }

}
