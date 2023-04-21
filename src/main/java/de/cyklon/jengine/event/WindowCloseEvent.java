package de.cyklon.jengine.event;

public interface WindowCloseEvent extends Event {

    /**
     * called when the GameWindow was closed
     */
    void onWindowClose();

}
