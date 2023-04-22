package de.cyklon.jengine.resource;

import java.io.File;

public interface IResourceManager {

    /**
     * load a file resource from your resource directory
     * <p>
     * the path is also used as name
     * @param path the path inside the resource directory
     * @return the Resource object is loaded
     */
    Resource loadResource(String path);
    /**
     * load a file resource from your resource directory with name to get the resource easily
     * @param name the name to identify the resource
     * @param path the path inside the resource directory
     * @return the Resource object is loaded
     */
    Resource loadResource(String name, String path);
    /**
     * load a file resource from the external file system
     * <p>
     * It is not recommended to load files from your desktop, for example, as they may not be available to other users
     * <p>
     * This method is intended to load files from, for example, the AppData folder
     * @param name the name to identify the resource
     * @param resource a File Object with a absolute path to a external file
     * @return the Resource object is loaded
     */
    Resource loadExternalResource(String name, File resource);

    /**
     * get a resource that has already been loaded with its unique name
     * @param name the name of the resource
     * @return the resource object
     */
    Resource getResource(String name);

    /**
     * unload a loaded resource
     * @param name the name of the resource to unload
     */
    void unloadResource(String name);

}
