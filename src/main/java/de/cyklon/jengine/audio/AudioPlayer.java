package de.cyklon.jengine.audio;

import de.cyklon.jengine.JEngine;
import de.cyklon.jengine.resource.Resource;

import javax.sound.sampled.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AudioPlayer implements AudioManager {

    private final JEngine engine;
    private static final Map<Long, AudioData> data = new HashMap<>();
    private static final Map<Long, AudioSetting> audioSettings = new HashMap<>();
    private static long audioID = 0;

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
    public AudioTask play(Resource resource) throws UnsupportedAudioFileException, IOException {
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
                    AudioSetting setting = getSetting(audio.getID());
                    if (setting.volume()<-56) throw new IllegalArgumentException("Minimum Volume is -56. the Volume " + setting.volume() + " is not allowed!");
                    if (setting.volume()>30) throw new IllegalArgumentException("Maximum Volume is 30. the Volume " + setting.volume() + " is not allowed!");

                    AudioFormat baseFormat = audioStream.getFormat();
                    AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate()*setting.pitch(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate()*setting.pitch(), false);
                    AudioInputStream din = AudioSystem.getAudioInputStream(targetFormat, audioStream);
                    SourceDataLine line = AudioSystem.getSourceDataLine(targetFormat);
                    line.open(targetFormat);

                    FloatControl panControl = (FloatControl) line.getControl(FloatControl.Type.PAN);
                    panControl.setValue(Math.min(Math.max(setting.pan(), panControl.getMinimum()), panControl.getMinimum()));

                    FloatControl gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(setting.volume()-30+gainControl.getMaximum());

                    line.start();
                    byte[] data = new byte[4096];
                    int nBytesRead;
                    final int volumeFactor = 8;
                    while ((nBytesRead = din.read(data, 0, data.length)) >= 0 && engine.getTask(id)!=null) {
                        for (int i = 0; i < nBytesRead; i += volumeFactor) {
                            short sample = (short) ((data[i+1] << 8) | (data[i] & 0xff));
                            sample = (short) Math.min(Short.MAX_VALUE, Math.max(Short.MIN_VALUE, sample * volumeFactor));
                            data[i] = (byte) (sample & 0xff);
                            data[i+1] = (byte) ((sample >> 8) & 0xff);
                        }
                        line.write(data, 0, nBytesRead);
                    }
                    line.drain();
                    line.stop();
                    din.close();
                } catch (LineUnavailableException | IOException e) {
                    e.printStackTrace();
                }
            } while (loop && engine.getTask(id)!=null);
            engine.removeTask(id);
        }, audio);
    }

    @Override
    public AudioTask play(Resource resource, boolean loop) throws UnsupportedAudioFileException, IOException {
        return play(audioFromResource(resource), loop);
    }

    @Override
    public Audio audioFromResource(final Resource resource) throws UnsupportedAudioFileException, IOException {
        String[] args = resource.getPath().split("\\.");
        final AudioType af = AudioType.getFromExtension(args[args.length-1]);
        if (af==null) throw new UnsupportedAudioFileException(args[args.length-1] + " is not supported. yous see a list of all supported types in de.cyklon.jengine.audio.AudioFormat");
        final long id = audioID;
        audioID++;
        final long length;
        final AudioInputStream ais = AudioSystem.getAudioInputStream(resource.getInputStream());
        length = (long) ((ais.getFrameLength()/ ais.getFormat().getFrameRate())*1000);
        return new Audio() {
            @Override
            public long getID() {
                return id;
            }

            @Override
            public Resource getResource() {
                return resource;
            }

            @Override
            public AudioType getFormat() {
                return af;
            }

            @Override
            public long getLength() {
                return length;
            }

            @Override
            public AudioInputStream getAudioStream() {
                return ais;
            }

            @Override
            public AudioTask play() {
                return AudioPlayer.this.play(this);
            }

            @Override
            public Audio setLoop(boolean loop) {
                AudioPlayer.this.setLoop(getID(), loop);
                return this;
            }

            @Override
            public Audio setVolume(float volume) {
                AudioPlayer.this.setVolume(getID(), volume);
                return this;
            }

            @Override
            public Audio setPitch(float pitch) {
                AudioPlayer.this.setPitch(getID(), pitch);
                return this;
            }

            @Override
            public Audio setPan(float pan) {
                AudioPlayer.this.setPan(getID(), pan);
                return this;
            }

            @Override
            public boolean isLoop() {
                return AudioPlayer.this.getSetting(getID()).loop();
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
                return getData(getID()).stopped();
            }

            @Override
            public Long getRunningTime() {
                long stopped = getStopTime();
                if (stopped==-1) stopped = System.currentTimeMillis();
                return stopped-getStartTime();
            }

            @Override
            public boolean isFinished() {
                return getData(getID()).stopped()!=-1;
            }

            @Override
            public void setFinishedListener(Runnable runnable) {
                setData(getID(), runnable);
            }

            @Override
            public Runnable getFinishedListener() {
                return getData(getID()).finishedListener();
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
                    setData(getID(), System.currentTimeMillis());
                    getFinishedListener().run();
                }
            }
        };
        engine.addTask(task);
        return task;
    }

    private void setData(long id, Runnable finishedListener) {
        data.put(id, setFinishedListener(data.getOrDefault(id, new AudioData(null, -1, null)), finishedListener));
    }

    private void setData(long id, long stopped) {
        data.put(id, setStopped(data.getOrDefault(id, new AudioData(null, -1, null)), stopped));
    }

    private void setData(long id, Audio audio) {
        data.put(id, setAudio(data.getOrDefault(id, new AudioData(null, -1, null)), audio));
    }

    private AudioData setFinishedListener(AudioData data, Runnable finishedListener) {
        return new AudioData(data.audio(), data.stopped(), finishedListener);
    }

    private AudioData setStopped(AudioData data, long stopped) {
        return new AudioData(data.audio(), stopped, data.finishedListener());
    }

    private AudioData setAudio(AudioData data, Audio audio) {
        return new AudioData(audio, data.stopped(), data.finishedListener());
    }

    private AudioData getData(long id) {
        AudioData audiData = data.get(id);
        if (audiData==null) {
            audiData = new AudioData(null, -1, () -> {});
            data.put(id, audiData);
        }
        return audiData;
    }

    private AudioSetting getSetting(long id) {
        AudioSetting setting = audioSettings.get(id);
        if (setting==null) {
            setting = new AudioSetting(false, 1.0F, 1.0F, 0.0F);
            audioSettings.put(id, setting);
        }
        return setting;
    }

    private AudioSetting setLoop(AudioSetting setting, boolean loop) {
        return new AudioSetting(loop, setting.volume(), setting.pitch(), setting.pan());
    }

    private AudioSetting setVolume(AudioSetting setting, float volume) {
        return new AudioSetting(setting.loop(), volume, setting.pitch(), setting.pan());
    }

    private AudioSetting setPitch(AudioSetting setting, float pitch) {
        return new AudioSetting(setting.loop(), setting.volume(), pitch, setting.pan());
    }

    private AudioSetting setPan(AudioSetting setting, float pan) {
        return new AudioSetting(setting.loop(), setting.volume(), setting.pitch(), pan);
    }

    private void setLoop(long id, boolean loop) {
        audioSettings.put(id, setLoop(getSetting(id), loop));
    }

    private void setVolume(long id, float volume) {
        audioSettings.put(id, setVolume(getSetting(id), volume));
    }

    private void setPitch(long id, float pitch) {
        audioSettings.put(id, setPitch(getSetting(id), pitch));
    }

    private void setPan(long id, float pan) {
        audioSettings.put(id, setPan(getSetting(id), pan));
    }

    private record AudioData(Audio audio, long stopped, Runnable finishedListener) {}
    private record AudioSetting(boolean loop, float volume, float pitch, float pan) {}
}
