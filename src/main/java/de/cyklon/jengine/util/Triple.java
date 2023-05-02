package de.cyklon.jengine.util;

public class Triple<t1, t2, t3> {

    private t1 first;
    private t2 second;
    private t3 third;

    public Triple(t1 first, t2 second, t3 third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public t1 getFirst() {
        return first;
    }

    public t2 getSecond() {
        return second;
    }

    public t3 getThird() {
        return third;
    }

    public void setFirst(t1 first) {
        this.first = first;
    }

    public void setSecond(t2 second) {
        this.second = second;
    }

    public void setThird(t3 third) {
        this.third = third;
    }
}
