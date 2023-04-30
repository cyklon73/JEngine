package de.cyklon.jengine.audio;

import de.cyklon.jengine.resource.Resource;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface Audio {

    /**
     * you can completely ignore this method, its only important for intern usage
     */
    long getID();

    /**
     * @return the resource file of the audio
     */
    Resource getResource();

    /**
     * @return the format of the audio (mp3, ogg...)
     */
    AudioType getFormat();

    /**
     * @return the length of the Audio in milliseconds
     */
    long getLength();

    /**
     * @return a audio stream from the audio file
     */
    AudioInputStream getAudioStream();


    AudioTask play();

    Audio setLoop(boolean loop);
    Audio setVolume(float volume);
    Audio setPitch(float pitch);
    Audio setPan(float pan);

    boolean isLoop();

}
