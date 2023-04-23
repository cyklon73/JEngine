package de.cyklon.jengine.audio;

import de.cyklon.jengine.audio.Audio;
import de.cyklon.jengine.resource.Resource;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface AudioManager {

    void play(Audio audio) throws UnsupportedAudioFileException, IOException;

    void play(Resource resource) throws UnsupportedAudioFileException, IOException;

    Audio audioFromResource(Resource resource) throws UnsupportedAudioFileException;

}
