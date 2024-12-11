package model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

/**
 * Utility class for retrieving a list of available songs from the music directory.
 *
 * @author Jacob Klymenko
 * @version 1.0
 */
public final class MusicUtil {

    /**
     * Retrieves a list of all songs in the "backgroundMusic" directory from the resources directory.
     *
     * @param theMusicDirectory the directory containing music files.
     * @return an {@link ArrayList} of song file names found in the directory.
     * @throws IOException if an error occurs while accessing the files in the directory.
     */
    public static ArrayList<String> getSongList(final String theMusicDirectory)
            throws IOException, URISyntaxException {
        ArrayList<String> songList = new ArrayList<>();
        URL resourceUrl = MusicUtil.class.getResource(theMusicDirectory);

        if (resourceUrl == null) {
            throw new IOException("Resource directory not found: " + theMusicDirectory);
        }

        if (resourceUrl.getProtocol().equals("jar")) {
            try (FileSystem fs = FileSystems.newFileSystem(resourceUrl.toURI(), Collections.emptyMap())) {
                Path path = fs.getPath(theMusicDirectory);
                try (Stream<Path> files = Files.walk(path)) {
                    files.filter(Files::isRegularFile)
                            .filter(file -> file.toString().endsWith(".wav"))
                            .forEach(file -> songList.add(file.getFileName().toString()));
                }
            } catch (URISyntaxException e) {
                throw new IOException("Error accessing resource: " + theMusicDirectory, e);
            }
        } else {
            Path path = Paths.get(resourceUrl.toURI());
            try (Stream<Path> files = Files.walk(path)) {
                files.filter(Files::isRegularFile)
                        .filter(file -> file.toString().endsWith(".wav"))
                        .forEach(file -> songList.add(file.getFileName().toString()));
            }
        }
        return songList;
    }
}
