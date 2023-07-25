package de.cyklon.jengine.data;

import java.io.Serializable;

public class TestClass implements Serializable {

    private final String s1 = "hello";
    private final String s2;

    public TestClass(String s2) {
        this.s2 = s2;
    }

}
