package view;

import controller.GameListener;
import model.Maze;
import model.Player;
import model.Room;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.*;

/**
 * A panel that represents the main screen of the maze game. It includes components for the game
 * display, questions, status bar, and key panel.
 *
 * @author Mathew Miller
 * @author Jacob Klymenko (Javadoc)
 * @version 1.0
 */
public final class MazeScreenPanel extends JPanel {

    /** Panel for rendering the game visuals. */
    private final GamePanel myGamePanel;

    /** Panel for displaying questions to the player. */
    private final QuestionPanel myQuestionPanel;

    /** Panel for showing the player's status and other game details. */
    private final StatusBarPanel myStatusBarPanel;

    /**
     * Constructs a maze screen panel with the specified game listener.
     *
     * @param theGameListener the listener for game events
     */
    public MazeScreenPanel(final GameListener theGameListener) {
        setSize(1200, 700);
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        setVisible(false);

        GridBagConstraints c = new GridBagConstraints();
        GridBagConstraints c2 = new GridBagConstraints();
        GridBagConstraints c3 = new GridBagConstraints();
        GridBagConstraints c4 = new GridBagConstraints();

        myQuestionPanel = new QuestionPanel(theGameListener);
        myStatusBarPanel = new StatusBarPanel(theGameListener);
        myGamePanel = new GamePanel(theGameListener);
        KeyPanel keyPanel = new KeyPanel();

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = NORTHEAST;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        add(myQuestionPanel, c);

        c2.gridx = 1;
        c2.gridy = 1;
        c2.anchor = SOUTHEAST;
        c2.insets = new Insets(5, 5, 5, 5);
        c2.weightx = 1.0;
        c2.weighty = 1.0;
        c2.fill = GridBagConstraints.BOTH;
        add(myStatusBarPanel, c2);

        c3.gridx = 0;
        c3.gridy = 1;
        c3.anchor = SOUTHWEST;
        c3.insets = new Insets(5, 5, 5, 5);
        c3.weightx = 1.0;
        c3.weighty = 1.0;
        c3.fill = GridBagConstraints.BOTH;
        add(keyPanel, c3);

        c4.gridx = 0;
        c4.gridy = 0;
        c4.anchor = NORTHWEST;
        c4.insets = new Insets(5, 5, 5, 5);
        c4.weightx = 1.0;
        c4.weighty = 1.0;
        c4.fill = GridBagConstraints.BOTH;
        add(myGamePanel, c4);
    }

    /**
     * Updates the panel to display the given maze and its current state.
     *
     * @param theMaze the maze to be displayed
     */
    public void setMaze(final Maze theMaze) {
        Room selectedRoom = theMaze.getCurrentlySelectedRoom();
        if (selectedRoom != null) {
            if (selectedRoom.isVisible()) {
                myQuestionPanel.setQuestion(null);
            } else {
                myQuestionPanel.setQuestion(selectedRoom.getQuestion());
            }
        } else {
            myQuestionPanel.setQuestion(null);
        }

        myGamePanel.setMaze(theMaze);
    }

    /**
     * Updates the status bar with the given player's information.
     *
     * @param thePlayer the player whose information is displayed
     */
    public void setPlayer(final Player thePlayer){
        myStatusBarPanel.setPlayerInfo(thePlayer);
    }

    /**
     * Enables or disables the hint functionality in the status bar.
     *
     * @param theEnabled true to enable hints, false to disable
     */
    public void setHintEnabled(final boolean theEnabled){
        myStatusBarPanel.setHintEnabled(theEnabled);
    }

}
