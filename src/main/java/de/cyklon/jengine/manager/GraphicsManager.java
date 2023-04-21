package de.cyklon.jengine.manager;

public interface GraphicsManager {

    /**
     * Checks if vertical synchronization (Vsync) is enabled. Vsync is a display option that
     * synchronizes the frames rendered by the graphics card with the refresh rate of the monitor,
     * preventing issues such as screen tearing and stuttering.
     *
     * @return true if Vsync is enabled, false otherwise
     */
    boolean useVsync();

    /**
     * Enables or disables vertical synchronization (Vsync). Vsync is a display option that
     * synchronizes the frames rendered by the graphics card with the refresh rate of the monitor,
     * preventing issues such as screen tearing and stuttering.
     *
     * @param vsync true to enable Vsync, false to disable it
     */
    void setVsync(boolean vsync);

    /**
     * @return the max FPS rate
     */
    double getMaxFPS();

    /**
     * Set the max FPS of the game.
     * <p>
     * use Constants.UNLIMITED_FPS for unlimited FPS
     * @param fps the FPS rate
     */
    void setMaxFPS(double fps);

    /**
     * Gets the current frames per second (FPS) of the game. FPS is a measure of how many frames are
     * being rendered by the graphics card each second, and a higher FPS generally results in
     * smoother gameplay.
     *
     * @return the FPS as an integer
     */
    int getFPS();

    /**
     * Gets the current frames per second (FPS) of the game as a double. FPS is a measure of how many
     * frames are being rendered by the graphics card each second, and a higher FPS generally results
     * in smoother gameplay.
     *
     * @return the FPS as a double
     */
    double getFPSasDouble();

    /**
     * Gets the window manager associated with this graphics manager. The window manager is responsible
     * for creating and managing the game window.
     *
     * @return the window manager
     */
    WindowManager getWindowManager();
}
