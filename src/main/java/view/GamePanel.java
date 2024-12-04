package view;

import controller.GameListener;
import model.Maze;

import javax.swing.*;
import java.awt.*;

/**
 * The main game panel for displaying and managing the maze visual panel.
 *
 * @author Mathew Miller
 * @version 1.0
 */
public final class GamePanel extends JPanel {

    /** Listener for handling game-related events. */
    private final GameListener myGameListener;

    /** Panel for rendering the maze visuals. */
    private MazeVisualPanel myMazeVisualPanel;

    /**
     * Constructs a game panel with the specified game listener.
     *
     * @param theGameListener the listener for game events
     */
    public GamePanel(final GameListener theGameListener) {
        myGameListener = theGameListener;
        setBackground(Color.BLACK);
        setBorder(new RoundedBorder(40));
        setVisible(true);
    }

    /**
     * Sets the maze to be displayed on the panel. Updates the visuals accordingly.
     *
     * @param theMaze the maze to be displayed
     */
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
