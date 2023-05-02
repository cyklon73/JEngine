package de.cyklon.jengine.os;

import java.io.File;
import java.io.IOException;

public enum OS {

    WINDOWS("windows", new Windows()),
    MAC("mac", new MacOS()),
    LINUX("linux", new Linux()),
    UNSUPPORTED("unsupported", new Unknown());


    private final String name;
    private final OperatingSystem system;

    OS(String name, OperatingSystem system) {
        this.name = name;
        this.system = system;
    }

    public String getName() {
        return name;
    }


    public OperatingSystem getOperatingSystem() {
        return system;
    }

    public static OS getOS() {
        final String currentOS = System.getProperty("os.name").toLowerCase();

        if (currentOS.startsWith("windows")) return WINDOWS;
        else if (currentOS.startsWith("mac")) return MAC;
        else if (currentOS.startsWith("linux")) return LINUX;
        else return UNSUPPORTED;
    }

    private static class Linux implements OperatingSystem {
        @Override
        public void shutdown() throws IOException {
            executeCommand("shutdown -h now");
        }

        @Override
        public void restart() throws IOException {
            executeCommand("reboot");
        }

        @Override
        public File getAppdata() {
            return new File(System.getProperty("user.home") + File.separator + ".config" + File.separator);
        }

        @Override
        public File getProgrammFiles() {
            return null;
        }
    }

    private static class MacOS implements OperatingSystem {
        @Override
        public void shutdown() throws IOException {
            executeCommand("shutdown -h now");
        }

        @Override
        public void restart() throws IOException {
            executeCommand("shutdown -r now");
        }

        @Override
        public File getAppdata() {
            return new File(System.getProperty("user.dir") + File.separator + "Liary" + File.separator + "Application Support" + File.separator);
        }

        @Override
        public File getProgrammFiles() {
            return null;
        }
    }

    private static class Windows implements OperatingSystem {
        @Override
        public void shutdown() throws IOException {
            executeCommand("shutdown.exe -s -t 0");
        }

        @Override
        public void restart() throws IOException {
            executeCommand("shutdown.exe -r -t 0");
        }

        @Override
        public File getAppdata() {
            return new File(System.getenv("APPDATA") + File.separator);
        }

        @Override
        public File getProgrammFiles() {
            return null;
        }
    }

    private static class Unknown implements OperatingSystem {

        @Override
        public void shutdown() {

        }

        @Override
        public void restart() {

        }

        @Override
        public void executeCommand(String command) {

        }

        @Override
        public File getAppdata() {
            return null;
        }

        @Override
        public File getProgrammFiles() {
            return null;
        }
    }

}
