package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.MusicUtil;

/**
 * Unit tests for the MusicUtil class testing the behavior of the MusicUtil class, specifically the
 * getSongList method. It ensures that the method behaves as expected under different conditions,
 * such as when the directory is empty, contains files, or contains non-music files. The tests also
 * verify that the method handles errors correctly, such as when the directory does not exist.
 *
 * @author Jacob Klymenko
 * @version 1.0
 */
public final class MusicUtilTest {

    /** Temporary directory used for testing music file operations. */
    private Path myTempMusicDir;

    /**
     * Sets up a temporary directory for the test methods to use. This method is called before
     * each test case is executed.
     *
     * @throws IOException If an I/O error occurs during directory creation.
     */
    @BeforeEach
    public void setUp() throws IOException {
        myTempMusicDir = Files.createTempDirectory("testMusicDirectory");
    }

    /**
     * Cleans up the temporary directory after each test case. This method is called after each
     * test case is executed.
     *
     * @throws IOException If an I/O error occurs during file deletion.
     */
    @AfterEach
    public void tearDown() throws IOException {
        if (myTempMusicDir != null) {
            Files.walk(myTempMusicDir)
                    .map(Path::toFile)
                    .forEach(file -> file.delete());
            myTempMusicDir.toFile().delete();
        }
    }

    /**
     * Tests the behavior of the getSongList method when the directory is empty. Ensures that the
     * returned song list is not null and is empty.
     *
     * @throws IOException If an I/O error occurs while fetching the song list.
     */
    @Test
    public void testGetSongListEmptyDirectory() throws IOException {
        List<String> songList = MusicUtil.getSongList(myTempMusicDir.toString());
        assertNotNull(songList, "Song list should not be null");
        assertTrue(songList.isEmpty(), "Song list should be empty for an empty directory");
    }

    /**
     * Tests the behavior of the getSongList method when the directory contains music files.
     * Ensures that the returned song list contains the correct files.
     *
     * @throws IOException If an I/O error occurs while fetching the song list.
     */
    @Test
    public void testGetSongListWithFiles() throws IOException {
        Files.createFile(myTempMusicDir.resolve("song1.wav"));
        Files.createFile(myTempMusicDir.resolve("song2.wav"));

        List<String> songList = MusicUtil.getSongList(myTempMusicDir.toString());

        assertNotNull(songList, "Song list should not be null");
        assertEquals(2, songList.size(), "Song list should contain 2 songs");
        assertTrue(songList.contains("song1.wav"), "Song list should contain 'song1.wav'");
        assertTrue(songList.contains("song2.wav"), "Song list should contain 'song2.wav'");
    }

    /**
     * Tests the behavior of the getSongList method when an invalid directory path is provided.
     * Ensures that an IOException is thrown for a non-existent directory.
     */
    @Test
    public void testGetSongListDirectoryNotFound() {
        String nonExistentPath = "invalidDirectory";

        assertThrows(IOException.class, () -> MusicUtil.getSongList(nonExistentPath),
                "Expected IOException for a non-existent directory");
    }

    /**
     * Tests the behavior of the getSongList method when the directory contains non-music files.
     * Ensures that only valid music files (i.e., with the .wav extension) are included in the
     * returned list.
     *
     * @throws IOException If an I/O error occurs while fetching the song list.
     */
    @Test
    public void testGetSongListNonMusicFiles() throws IOException {
        Files.createFile(myTempMusicDir.resolve("song1.wav"));
        Files.createFile(myTempMusicDir.resolve("song2.wav"));
        Files.createFile(myTempMusicDir.resolve("song3.txt"));  // Non-music file

        List<String> songList = MusicUtil.getSongList(myTempMusicDir.toString());

        assertNotNull(songList, "Song list should not be null");
        assertEquals(2, songList.size(), "Song list should contain only .wav files");
        assertTrue(songList.contains("song1.wav"), "Song list should contain 'song1.wav'");
        assertTrue(songList.contains("song2.wav"), "Song list should contain 'song2.wav'");
        assertFalse(songList.contains("song3.txt"), "Song list should not contain 'song3.txt'");
    }
}
