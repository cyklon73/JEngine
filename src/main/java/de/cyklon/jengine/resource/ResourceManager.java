package de.cyklon.jengine.resource;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ResourceManager implements IResourceManager {

    private static IResourceManager INSTANCE;
    private final Map<String, Resource> resourceMap;

    private ResourceManager() {
        this.resourceMap = new HashMap<>();
    }

    public static synchronized IResourceManager getInstance() {
        if (INSTANCE == null) INSTANCE = new ResourceManager();
        return INSTANCE;
    }

    @Override
    public Resource loadResource(String path) {
        return initResource(path, path, Resource.Type.INTERNAL);
    }

    @Override
    public Resource loadResource(String name, String path) {
        return initResource(name, path, Resource.Type.INTERNAL);
    }

    @Override
    public Resource loadExternalResource(String name, File resource) {
        return initResource(name, resource.getAbsolutePath(), Resource.Type.EXTERNAL);
    }

    @Override
    public Resource getResource(String name) {
        return resourceMap.get(name);
    }

    @Override
    public void unloadResource(String name) {
        resourceMap.remove(name);
    }

    public int check() {
        return 1;
    }

    private Resource initResource(final String name, final String path, final Resource.Type type) {
        final UUID id = UUID.randomUUID();
        return new Resource() {
            @Override
            public Type getType() {
                return type;
            }

            @Override
            public String getPath() {
                return path;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getFileName() {
                return getFile().getName();
            }

            @Override
            public File getFile() {
                return new File(getPath());
            }

            @Override
            public URL getURL() throws MalformedURLException {
                return getType()==Type.INTERNAL ? getClass().getClassLoader().getResource(getPath()) : getFile().toURI().toURL();
            }

            @Override
            public InputStream getInputStream() throws FileNotFoundException {
                InputStream in = getType()==Type.INTERNAL ? getClass().getClassLoader().getResourceAsStream(getPath()) : new FileInputStream(getFile());
                if (in==null) {
                    ResourceManager.this.unloadResource(getName());
                    throw new FileNotFoundException("Resource file from resource " + getName() + " (" + getResourceID() + ") not found. this resource is unloaded");
                }
                return in;
            }

            @Override
            public UUID getResourceID() {
                return id;
            }

            @Override
            public void export(File file) throws IOException {
                export(file, false);
            }

            @Override
            public void export(File file, boolean replace) throws IOException {
                if (!replace && file.exists()) return;
                try (InputStream in = getInputStream(); OutputStream out = new BufferedOutputStream(Files.newOutputStream(file.toPath()))) {
                       byte[] buffer = new byte[4096];
                       int length;
                       while ((length = in.read(buffer)) >= 0) out.write(buffer, 0, length);
                }
            }
        };
    }
}
