package view;

import controller.GameListener;
import model.Maze;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private MazeVisualPanel myMazeVisualPanel;
    public GamePanel(GameListener myGameListener) {

        setBackground(Color.BLACK);
        setBorder(new RoundedBorder(40));
        //add(new MazeVisualPanel(5, 8));
        setVisible(true);


    }


    public void setMaze(final Maze theMaze) {
        myMazeVisualPanel = new MazeVisualPanel(theMaze);
        this.add(myMazeVisualPanel);
    }
}
