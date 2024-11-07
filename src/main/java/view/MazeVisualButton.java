package view;

import model.BooleanQuestion;
import model.MultipleChoiceQuestion;
import model.Room;
import model.TextInputQuestion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A button that represents a grid on the maze.
 */
public class MazeVisualButton extends JButton {
    /** The room that this button represents. */
    private Room myRoom;
    /** The row that this room is located on. */
    private int myRow;
    /** The col that this room is located on. */
    private int myCol;

    /**
     * Creates a MazeVisualButton.
     *
     * @param room the room this represents.
     */
    public MazeVisualButton(Room room, final int theRow, final int theCol) {
        myRoom = room;
        myRow = theRow;
        myCol = theCol;
        updateVisualImage();

        setBorder(null);

    }

    /**
     * Updates the visual image of this button.
     */
    public void updateVisualImage() {
        setIcon(new ImageIcon(myRoom.getRoomImage().getScaledInstance( 90, 90, Image.SCALE_SMOOTH)));
    }

    /**
     * Gets the row this room is on.
     *
     * @return the row of this room.
     */
    public int getRow() {
        return myRow;
    }

    /**
     * Gets the col this room is on.
     *
     * @return the col of this room.
     */
    public int getCol() {
        return myCol;
    }

    // button should tell the panel it has been clicked

}
