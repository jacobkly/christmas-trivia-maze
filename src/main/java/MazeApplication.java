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
//        var questionsFromDatabase = QuestionFactory.getQuestionsFromDatabase();
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
