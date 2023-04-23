package de.cyklon.jengine.util;

public interface Task {

    long getID();
    Thread getThread();

    void finished();

}
