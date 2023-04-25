package de.cyklon.jengine.audio;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.resource.Resource;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AudioPlayer implements AudioManager {

    private final JEngine engine;
    private final Map<Long, Long> stopped = new HashMap<>();
    private final Map<Long, Runnable> finishListener = new HashMap<>();

    public AudioPlayer(JEngine engine) {
        if (engine==null) throw new RuntimeException("Engine cannot be null");
        this.engine = engine;
    }

    @Override
    public AudioTask play(AudioTask task) {
        return play(task, false);
    }

    @Override
    public AudioTask play(Audio audio) {
        return play(audio, false);
    }

    @Override
    public AudioTask play(Resource resource) throws UnsupportedAudioFileException {
        return play(resource, false);
    }

    @Override
    public AudioTask play(AudioTask task, boolean loop) {
        AudioTask at = play(task.getAudio(), loop);
        at.setFinishedListener(task.getFinishedListener());
        return at;
    }

    @Override
    public AudioTask play(final Audio audio, final boolean loop) {
        final long id = engine.getNextTaskID();
        return createTask(id, loop, () -> {
            do {
                try (AudioInputStream audioStream = audio.getAudioStream();) {
                    AudioFormat baseFormat = audioStream.getFormat();
                    AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
                    AudioInputStream din = AudioSystem.getAudioInputStream(targetFormat, audioStream);
                    SourceDataLine line = AudioSystem.getSourceDataLine(targetFormat);
                    line.open(targetFormat);
                    line.start();
                    byte[] data = new byte[4096];
                    int nBytesRead;
                    while ((nBytesRead = din.read(data, 0, data.length)) >= 0 && engine.getTask(id)!=null) {
                        line.write(data, 0, nBytesRead);
                    }
                    line.drain();
                    line.stop();
                    din.close();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                    e.printStackTrace();
                }
            } while (loop && engine.getTask(id)!=null);
            engine.removeTask(id);
        }, audio);
    }

    @Override
    public AudioTask play(Resource resource, boolean loop) throws UnsupportedAudioFileException {
        return play(audioFromResource(resource), loop);
    }

    @Override
    public Audio audioFromResource(Resource resource) throws UnsupportedAudioFileException {
        String[] args = resource.getPath().split("\\.");
        final AudioType af = AudioType.getFromExtension(args[args.length-1]);
        if (af==null) throw new UnsupportedAudioFileException(args[args.length-1] + " is not supported. yous see a list of all supported types in de.cyklon.jengine.audio.AudioFormat");
        return new Audio() {
            @Override
            public Resource getResource() {
                return resource;
            }

            @Override
            public AudioType getFormat() {
                return af;
            }

            @Override
            public long getLength() throws IOException {
                try {
                    AudioInputStream audioStream = getAudioStream();
                    return (long) ((audioStream.getFrameLength()/audioStream.getFormat().getFrameRate())*1000);
                } catch (UnsupportedAudioFileException ignored) {} //impossible
                return 0;
            }

            @Override
            public AudioInputStream getAudioStream() throws IOException, UnsupportedAudioFileException {
                return AudioSystem.getAudioInputStream(resource.getInputStream());
            }
        };
    }

    @Override
    public void stop(long id) {
        engine.cancelTask(id);
    }

    @Override
    public void stop(AudioTask task) {
        stop(task.getID());
    }

    private AudioTask createTask(final long id, final boolean loop, final Runnable runnable, final Audio audio) {
        final Thread thread = new Thread(runnable);
        final long started = System.currentTimeMillis();
        AudioTask task = new AudioTask() {
            @Override
            public Boolean isLoop() {
                return loop;
            }

            @Override
            public Audio getAudio() {
                return audio;
            }

            @Override
            public Long getStartTime() {
                return started;
            }

            @Override
            public Long getStopTime() {
                return stopped.getOrDefault(getID(), -1L);
            }

            @Override
            public Long getRunningTime() {
                long stopped = getStopTime();
                if (stopped==-1) stopped = System.currentTimeMillis();
                return stopped-getStartTime();
            }

            @Override
            public boolean isFinished() {
                return stopped.get(getID())!=null;
            }

            @Override
            public void setFinishedListener(Runnable runnable) {
                finishListener.put(getID(), runnable);
            }

            @Override
            public Runnable getFinishedListener() {
                return finishListener.getOrDefault(getID(), () -> {});
            }

            @Override
            public long getID() {
                return id;
            }

            @Override
            public Thread getThread() {
                return thread;
            }

            @Override
            public void finished() {
                if (!isFinished()) {
                    stopped.put(getID(), System.currentTimeMillis());
                    getFinishedListener().run();
                }
            }
        };
        engine.addTask(task);
        return task;
    }
}
