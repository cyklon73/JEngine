package de.cyklon.jengine.audio;

import de.cyklon.jengine.resource.Resource;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface Audio {

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
    long getLength() throws UnsupportedAudioFileException, IOException;

    /**
     * @return a audio stream from the audio file
     */
    AudioInputStream getAudioStream() throws IOException, UnsupportedAudioFileException;

}
