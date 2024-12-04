package view;

import model.*;

import javax.swing.*;
import java.awt.*;

/**
 * A button that represents a grid on the maze.
 */
public final class MazeVisualButton extends JButton {

    /** The room that this button represents. */
    private final Room myRoom;

    /**
     * Creates a MazeVisualButton.
     *
     * @param room the room this represents.
     */
    public MazeVisualButton(Room room, final int theRow, final int theCol) {
        myRoom = room;
        updateVisualImage();
        setBorder(null);
    }

    /**
     * Updates the visual image of this button.
     */
    public void updateVisualImage() {
        int dimension = 90;
        Image img = RoomImageMerger.MergeImage(myRoom.getRoomInfo());
        setIcon(new ImageIcon(img.getScaledInstance( dimension, dimension, Image.SCALE_SMOOTH)));
        setDisabledIcon(new ImageIcon(img.getScaledInstance( dimension, dimension, Image.SCALE_SMOOTH)));
    }
}
