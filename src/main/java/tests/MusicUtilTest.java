package tests;

import static org.junit.jupiter.api.Assertions.*;

import model.MusicUtil;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;

/**
 * Unit tests for the MusicUtil class. These tests check the behavior of the getSongList method for
 * various directory scenarios.
 *
 * @author Jacob Klymenko
 * @version 1.0
 */
class MusicUtilTest {

    /** Directory containing the test music files, relative to resources directory. */
    private static final String TEST_DIRECTORY = "/musicTests/testMusic";

    /**
     * Tests the getSongList method with a valid directory containing .wav files. It verifies that
     * the correct songs are returned.
     *
     * @throws Exception if an error occurs while retrieving the song list
     */
    @Test
    void testGetSongList_ValidDirectory() throws Exception {
        ArrayList<String> songList = MusicUtil.getSongList(TEST_DIRECTORY);

        // Assert
        assertNotNull(songList);
        assertEquals(2, songList.size());
        assertTrue(songList.contains("test1.wav"));
        assertTrue(songList.contains("test2.wav"));
    }

    /**
     * Tests the getSongList method with a directory containing non-.wav files. It verifies that
     * the returned song list is empty.
     *
     * @throws Exception if an error occurs while retrieving the song list
     */
    @Test
    void testGetSongList_NoWavFiles() throws Exception {
        ArrayList<String> songList = MusicUtil.getSongList("/musicTests/nonWavFiles");

        assertNotNull(songList);
        assertTrue(songList.isEmpty());
    }

    /**
     * Tests the getSongList method with a non-existing directory. It verifies that an IOException
     * is thrown for an invalid directory.
     */
    @Test
    void testGetSongList_InvalidDirectory() {
        assertThrows(IOException.class, () -> MusicUtil.getSongList("invalidDirectory"));
    }
}