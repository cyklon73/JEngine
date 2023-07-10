package de.cyklon.jengine.util;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class RunnableTask implements Runnable {

    private long lastExecution;

    @Setter
    private long cooldown;


    public RunnableTask() {
        this(0);
    }

    public RunnableTask(long cooldown) {
        this.lastExecution = 0;
        this.cooldown = cooldown;
    }


    public abstract void execute();

    @Override
    public void run() {
        long now = System.currentTimeMillis();
        if (now>=lastExecution+cooldown) {
            this.lastExecution = now;
            execute();
        }
    }


}
