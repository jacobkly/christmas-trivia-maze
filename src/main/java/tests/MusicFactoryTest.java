package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.MusicFactory;

/**
 * Unit tests for the MusicFactory class, validating its functionality including loading songs,
 * cycling through the song list, and handling errors for missing or unsupported audio files. The
 * tests ensure that the correct song is loaded, that the song list cycles as expected, and that
 * appropriate exceptions are thrown for invalid audio files.
 *
 * @author Jacob Klymenko
 * @version 1.0
 */
public class MusicFactoryTest {

    private MusicFactory myMusicFactory;

    /**
     * Initializes the MusicFactory with a list containing only the first test song before each test.
     * This simplifies the testing by focusing only on the first song.
     */
    @BeforeEach
    public void setUp() {
        ArrayList<String> songList = new ArrayList<>();
        // This song should exist in the resources folder to for correct tests
        songList.add("Unit Test Song.wav");
        myMusicFactory = new MusicFactory(songList);
    }

    /**
     * Tests that the loadCurrentSong method loads the first song correctly. It verifies that the
     * returned Clip is not null.
     *
     * @throws UnsupportedAudioFileException if the song cannot be loaded due to an unsupported
     *                                       file format.
     * @throws IOException if an I/O error occurs while loading the song.
     * @throws LineUnavailableException if the audio line required for playback is unavailable.
     */
    @Test
    public void testLoadCurrentSong() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException {
        final Clip clip = myMusicFactory.loadCurrentSong();

        assertNotNull(clip, "Clip should not be null when loading a song.");
    }

    /**
     * Tests that the updateToNextSong method does not fail when called, even though only one song
     * is present in the list. The current song index should remain 0.
     */
    @Test
    public void testUpdateToNextSongWithOneSong() {
        myMusicFactory.updateToNextSong();
        assertEquals(0, myMusicFactory.getCurrSongIndex(),
                "Current song index should remain 0 since there's only one song in the list.");
    }

    /**
     * Tests the getCurrSongIndex method to ensure it correctly returns the current song index.
     * Initially, the song index should be 0, and after calling updateToNextSong, it should remain 0,
     * as there's only one song in the list.
     */
    @Test
    public void testGetCurrSongIndex() {
        // Ensure the initial index is 0
        assertEquals(0, myMusicFactory.getCurrSongIndex(),
                "Initial current song index should be 0.");

        // Calling updateToNextSong should not change the index for a single song
        myMusicFactory.updateToNextSong();
        assertEquals(0, myMusicFactory.getCurrSongIndex(),
                "Current song index should remain 0 after calling updateToNextSong on " +
                        "a single song list.");
    }

    /**
     * Tests that the loadCurrentSong method throws an UnsupportedAudioFileException when the song
     * file cannot be found.
     */
    @Test
    public void testLoadCurrentSongFileNotFound() {
        ArrayList<String> invalidSongList = new ArrayList<>();
        invalidSongList.add("nonexistentSong.wav");

        MusicFactory invalidMusicFactory = new MusicFactory(invalidSongList);

        assertThrows(UnsupportedAudioFileException.class, invalidMusicFactory::loadCurrentSong,
                "Expected UnsupportedAudioFileException for a song that doesn't exist.");
    }

    /**
     * Tests that the loadCurrentSong method throws an UnsupportedAudioFileException when an
     * unsupported audio format is provided.
     */
    @Test
    public void testLoadCurrentSongUnsupportedFormat() {
        ArrayList<String> invalidFormatList = new ArrayList<>();
        invalidFormatList.add("unsupportedFormat.xyz");

        MusicFactory invalidMusicFactory = new MusicFactory(invalidFormatList);

        assertThrows(UnsupportedAudioFileException.class, invalidMusicFactory::loadCurrentSong,
                "Expected UnsupportedAudioFileException for an unsupported audio format.");
    }
}
