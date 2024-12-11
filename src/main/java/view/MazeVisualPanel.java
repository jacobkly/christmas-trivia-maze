package view;

import controller.GameListener;
import model.Maze;

import javax.swing.*;
import java.awt.*;

/**
 * A panel for visually representing the maze in the game.
 *
 * @author Cai Spidel
 * @author Mathew Miller
 * @version 1.0
 */
public final class MazeVisualPanel extends JPanel {

    /** Listener for game-related events. */
    private final GameListener myGameListener;

    /**
     * Constructs a MazeVisualPanel to display the given maze.
     *
     * @param theGameListener the listener for room click events
     * @param theMaze the maze to be displayed
     */
    public MazeVisualPanel(final GameListener theGameListener, final Maze theMaze) {
        myGameListener = theGameListener;
        setLayout(new GridLayout(theMaze.getRows(), theMaze.getCols()));

        for (int i = 0; i < theMaze.getRows(); i++) {
            for (int j = 0; j < theMaze.getCols(); j++) {
                var room = theMaze.getRoom(i, j);
                MazeVisualButton button = new MazeVisualButton(room, i, j);
                button.addActionListener(e -> myGameListener.onRoomClicked(room));
                add(button);
            }
        }
        updateVisualInfo(theMaze);
        setVisible(true);
    }

    /**
     * Updates the visuals of all maze components. Should be called to refresh the UI after any
     * changes to the maze's state.
     *
     * @param theMaze the maze whose state is reflected in the visuals
     */
    private void updateVisualInfo(final Maze theMaze) {
        theMaze.updateRoomVisibility();
        Component[] components = this.getComponents();
        for (Component component : components) {
            if (component instanceof MazeVisualButton) {
                MazeVisualButton button = (MazeVisualButton) component;
                button.updateVisualImage();
            }
        }
    }
}
