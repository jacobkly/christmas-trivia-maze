package model;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MusicFactory {

    private final ArrayList<String> mySongList;

    private int myCurrSongIndex;

    public MusicFactory(final ArrayList<String> theSongList) {
        mySongList = theSongList;
        myCurrSongIndex = 0;
    }

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

    /** gets next song, or loops back if at the end */
    public void updateToNextSong() {
        myCurrSongIndex = (myCurrSongIndex + 1) % mySongList.size();
    }

    public String getCurrentSongName() {
        return mySongList.get(myCurrSongIndex);
    }
}
