package model;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Manages a shuffled playlist of songs and provides functionality to load and navigate through them.
 *
 * @author Jacob Klymenko
 * @version 1.0
 */
public final class MusicFactory {

    /** The list of songs in the playlist. */
    private final ArrayList<String> mySongList;

    /** The index of the currently playing song. */
    private int myCurrSongIndex;

    /**
     * Constructs a MusicFactory with the given list of songs. The songs are shuffled upon
     * initialization.
     *
     * @param theSongList the list of songs to manage.
     */
    public MusicFactory(final ArrayList<String> theSongList) {
        mySongList = theSongList;
        Collections.shuffle(mySongList);
        myCurrSongIndex = 0;
    }

    /**
     * Loads the audio file of the currently selected song.
     *
     * @return a {@link Clip} object representing the audio of the current song.
     * @throws UnsupportedAudioFileException if the audio file format is unsupported.
     * @throws IOException if an error occurs while reading the audio file.
     * @throws LineUnavailableException if a clip line cannot be opened.
     */
    public Clip loadCurrentSong() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        String songName = mySongList.get(myCurrSongIndex);
        URL musicURL = MusicUtil.class.getClassLoader().getResource("backgroundMusic/" + songName);

        if (musicURL == null) {
            throw new UnsupportedAudioFileException("Unable to load audio file: " + songName);
        }

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicURL);
        Clip musicClip = AudioSystem.getClip();
        musicClip.open(audioStream);
        return musicClip;
    }

    /**
     * Advances to the next song in the playlist. Loops back to the beginning if at the end of the
     * playlist.
     */
    public void updateToNextSong() {
        myCurrSongIndex = (myCurrSongIndex + 1) % mySongList.size();
    }

    /**
     * Retrieves the index of the currently loaded song in the song list.
     *
     * @return the index of the current song in the list. The index is 0-based, with the first song
     *         in the list having an index of 0.
     */
    public int getCurrSongIndex() { return myCurrSongIndex; }
}
