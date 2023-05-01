package de.cyklon.jengine.resource;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.audio.Audio;
import de.cyklon.jengine.exception.UnsupportedFileException;
import de.cyklon.jengine.render.sprite.Sprite;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ResourceManager implements IResourceManager {

    private static IResourceManager INSTANCE;
    private static JEngine engine;
    private final Map<String, Resource> resourceMap;

    private ResourceManager() {
        this.resourceMap = new HashMap<>();
        String dir = engine.getName().replaceAll("\\s+", "_").toLowerCase();
        URL resource = getClass().getClassLoader().getResource(dir);

        /*URL url = getClass().getClassLoader().getResource(dir);
        if (url == null) {
            try {
                URI uri = getClass().getClassLoader().getResource("").toURI();
                Path path = Paths.get(uri).resolve(dir);
                Files.createDirectories(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }

    public static synchronized IResourceManager getInstance(JEngine engine) {
        if (engine==null) throw new IllegalStateException("You have to start the engine first!");
        ResourceManager.engine = engine;
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

    @Override
    public Resource reloadResource(String name) {
        Resource resource = getResource(name);
        if (resource==null) return null;
        unloadResource(name);
        return loadResource(name, resource.getPath());
    }

    private String getPath(String path) {
        return engine.getName().toLowerCase() + "/" + path;
    }
    private Resource initResource(final String name, final String path, final Resource.Type type) {
        final UUID id = UUID.randomUUID();
        Resource r = new Resource() {
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
                return getType()==Type.INTERNAL ? getClass().getClassLoader().getResource(ResourceManager.this.getPath(getPath())) : getFile().toURI().toURL();
            }

            @Override
            public InputStream getInputStream() throws FileNotFoundException {
                InputStream in = getType()==Type.INTERNAL ? getClass().getClassLoader().getResourceAsStream(ResourceManager.this.getPath(getPath())) : new FileInputStream(getFile());
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
            public String getHash() throws IOException {
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(getBytes());
                    return String.format("%032x", new BigInteger(1, md.digest()));
                } catch (NoSuchAlgorithmException ignored) {}
                return null;
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

            @Override
            public byte[] getBytes() throws IOException {
                byte[] bytes;
                try (InputStream in = getInputStream(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                    byte[] buffer = new byte[4096];
                    int length;
                    while ((length = in.read(buffer)) >= 0) out.write(buffer, 0, length);
                    bytes = out.toByteArray();
                }
                return bytes;
            }

            @Override
            public Audio getAudio() throws UnsupportedAudioFileException, IOException {
                return JEngine.getEngine().getAudioManger().audioFromResource(this);
            }

            @Override
            public Sprite getSprite() throws IOException, UnsupportedFileException {
                return JEngine.getEngine().getGraphicsManager().getSpriteRenderer().spriteFromResource(this);
            }
        };
        resourceMap.put(name, r);
        return r;
    }
}
