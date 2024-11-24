import controller.*;
import model.*;
import view.MazeViewFrame;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Main class for launching the Christmas Trivia Maze application. It sets the look and feel of
 * the user interface and initializes the game controller,  music functionality (if available),
 * and the maze view frame.
 *
 * @author Mathew Miller
 * @author Cai Spidel
 * @author Jacob Klymenko
 * @version 1.0
 */
public class MazeApplication {

    /**
     * The entry point for the Maze application. It sets the look and feel, initializes the game
     * controller, loads the song list, and configures the view with or without music.
     *
     * @param theArgs command-line arguments (not used)
     * @throws URISyntaxException if there is an error with URI syntax
     * @throws IOException if there is an error with I/O operations
     */
    public static void main(final String[] theArgs) throws URISyntaxException, IOException {
        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException |
                 InstantiationException | IllegalAccessException e) {
            System.out.println("Look and feel not supported");
        }
        GameController gameController = new GameController();
        ArrayList<String> songList = MusicUtil.getSongList();
        MazeViewFrame view;

        if (songList.isEmpty()) { // disable music feature
            System.out.println("No songs found");
            MusicController disabledMusic = new MusicController(new MusicFactory(new ArrayList<>()));
            view = new MazeViewFrame(gameController, disabledMusic);
        } else { // enable music feature
            MusicFactory musicFactory = new MusicFactory(songList);
            MusicController musicController = new MusicController(musicFactory);
            view = new MazeViewFrame(gameController, musicController);
            musicController.startPlaying();
        }
        gameController.setView(view);
        view.setResizable(false);
        view.setVisible(true);
    }
}
