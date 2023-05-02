package de.cyklon.jengine.os;

import java.io.File;
import java.io.IOException;

public interface OperatingSystem {

    public void shutdown() throws IOException;
    public void restart() throws IOException;
    public default void executeCommand(String command) throws IOException {
        Runtime.getRuntime().exec(command);
    };
    public File getAppdata();
    public File getProgrammFiles();

}
