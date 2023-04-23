package de.cyklon.jengine.audio;

import de.cyklon.jengine.audio.Audio;
import de.cyklon.jengine.resource.Resource;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface AudioManager {

    AudioTask play(AudioTask task);
    AudioTask play(Audio audio);

    AudioTask play(Resource resource) throws UnsupportedAudioFileException;
    AudioTask play(AudioTask task, boolean loop);
    AudioTask play(Audio audio, boolean loop);

    AudioTask play(Resource resource, boolean loop) throws UnsupportedAudioFileException;

    Audio audioFromResource(Resource resource) throws UnsupportedAudioFileException;

    void stop(long id);

    void stop(AudioTask task);

}
