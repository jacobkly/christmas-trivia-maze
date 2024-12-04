package view;

import controller.GameListener;
import model.Maze;

import javax.swing.*;
import java.awt.*;

public final class GamePanel extends JPanel {

    private final GameListener myGameListener;

    private MazeVisualPanel myMazeVisualPanel;

    public GamePanel(GameListener theGameListener) {
        myGameListener = theGameListener;
        setBackground(Color.BLACK);
        setBorder(new RoundedBorder(40));
        //add(new MazeVisualPanel(5, 8));
        setVisible(true);
    }

    public void setMaze(final Maze theMaze) {
        if (myMazeVisualPanel != null) {
            remove(myMazeVisualPanel);
        }

        myMazeVisualPanel = new MazeVisualPanel(myGameListener, theMaze);
        add(myMazeVisualPanel);
        revalidate();
        repaint();
    }
}
