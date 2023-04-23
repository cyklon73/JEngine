package de.cyklon.jengine.audio;

import de.cyklon.jengine.util.Task;

public interface AudioTask extends Task {

    Boolean isLoop();
    Audio getAudio();

    Long getStartTime();

    Long getStopTime();

    Long getRunningTime();

    boolean isFinished();

    void setFinishedListener(Runnable runnable);
    Runnable getFinishedListener();

}
