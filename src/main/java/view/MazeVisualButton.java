package view;

import model.*;

import javax.swing.*;
import java.awt.*;

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
        int dimension = 90;
        Image img = RoomImageMerger.MergeImage(myRoom.getRoomImage());
        setIcon(new ImageIcon(img.getScaledInstance( dimension, dimension, Image.SCALE_SMOOTH)));
        setDisabledIcon(new ImageIcon(img.getScaledInstance( dimension, dimension, Image.SCALE_SMOOTH)));
        setEnabled(myRoom.isAnswerable());
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
