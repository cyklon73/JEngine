package de.cyklon.jengine.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public interface Resource {

    /**
     * the type indicates whether the resource is in the internal resource directory or in the external file system
     * @return the resource type (INTERNAL/EXTERNAL)
     */
    Type getType();

    /**
     * @return The path of the resource
     */
    String getPath();

    /**
     * @return the name of the resource. if no name was specified when loading, the path is returned
     */
    String getName();

    /**
     * @return the filename of the resource
     */
    String getFileName();

    /**
     * @see java.io.File
     * @return the resource as java.io.File Object
     */
    File getFile();

    /**
     * @return the Resource URL
     * @throws MalformedURLException if the resource url is Malformed
     */
    URL getURL() throws MalformedURLException;

    /**
     * @return a input stream of the resource
     * @throws FileNotFoundException if the resource file is not found
     */
    InputStream getInputStream() throws FileNotFoundException;

    /**
     * each resource object has a unique id.
     * <p>
     * so you can better identify resources object
     * @return the unique resource id
     */
    UUID getResourceID();

    /**
     * each resource has a hash.
     * <p>
     * so you can identify a resource.
     * <p>
     * even if it's a different resource object, as long as it's the same resource the hash is the same
     */
    String getHash() throws IOException;


    /**
     * export a loaded resource file to the external file system
     * <p>
     * if the file already exists in the external file system, the file will not be exported
     * @param file a File Object with a absolute path in the external file system
     */
    void export(File file) throws IOException;

    /**
     * export a loaded resource file to the external file system
     * <p>
     * if the file already exists in the external file system, the file will not be exported unless the boolean replace is true
     * @param file a File Object with a absolute path in the external file system
     * @param replace if true file will be replaced
     */
    void export(File file, boolean replace) throws IOException;

    /**
     * @return the bytes of the resource file
     */
    byte[] getBytes() throws IOException;

    public static enum Type {
        INTERNAL,
        EXTERNAL
    }

}
