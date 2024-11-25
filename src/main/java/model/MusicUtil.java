package model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Utility class for retrieving a list of available songs from the music directory.
 *
 * @author Jacob Klymenko
 * @version 1.0
 */
public class MusicUtil {

    /**
     * Retrieves a list of all songs in the "backgroundMusic" directory.
     *
     * @param theMusicDirectory the directory containing music files.
     * @return an {@link ArrayList} of song file names found in the directory.
     * @throws IOException if an error occurs while accessing the files in the directory.
     */
    public static ArrayList<String> getSongList(final String theMusicDirectory) throws IOException {
        ArrayList<String> songList = new ArrayList<>();
        Path musicPath = Paths.get(theMusicDirectory);

        if (Files.exists(musicPath) && Files.isDirectory(musicPath)) {
            try (Stream<Path> files = Files.walk(musicPath)) {
                files.filter(Files::isRegularFile)
                        .filter(file -> file.toString().endsWith(".wav"))
                        .forEach(file -> songList.add(file.getFileName().toString()));
            } catch (final Exception theException) {
                throw new IOException("Directory does not exist or not a valid directory: " + theMusicDirectory);

            }
        }
        return songList;
    }
}
