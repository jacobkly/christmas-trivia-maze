package model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class MusicUtil {

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
