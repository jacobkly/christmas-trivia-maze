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
     * @return an {@link ArrayList} of song file names found in the directory.
     * @throws URISyntaxException if the music directory URL cannot be converted to a URI.
     * @throws IOException if an error occurs while accessing the files in the directory.
     */
    public static ArrayList<String> getSongList() throws URISyntaxException, IOException {
        ArrayList<String> songList = new ArrayList<>();
        String musicDirectory = "backgroundMusic";
        URL musicURL = MusicUtil.class.getClassLoader().getResource(musicDirectory);

        if (musicURL != null) {
            Path musicPath = Paths.get(musicURL.toURI());

            try (Stream<Path> files = Files.walk(musicPath)) {
                files.filter(Files::isRegularFile).forEach(
                        file -> songList.add(file.getFileName().toString())
                );
            } catch (IOException theException) {
                theException.printStackTrace();
            }
        }
        return songList;
    }
}
