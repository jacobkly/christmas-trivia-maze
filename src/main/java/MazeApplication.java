import controller.*;
import model.*;
import view.MazeViewFrame;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MazeApplication {

    public static void main(final String[] theArgs) throws URISyntaxException, IOException {
        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException |
                 InstantiationException | IllegalAccessException e) {
            System.out.println("Look and feel not supported");
        }

        ArrayList<String> songList = MusicUtil.getSongList();
        MusicFactory musicFactory = new MusicFactory(songList);
        MusicController musicController = new MusicController(musicFactory);
        musicController.startPlaying();

        var questionsFromDatabase = QuestionFactory.getQuestionsFromDatabase();

        GameController controller = new GameController();
        MazeViewFrame view = new MazeViewFrame(controller);
        controller.setView(view);
        view.setResizable(false);
        view.setVisible(true);
    }
}
