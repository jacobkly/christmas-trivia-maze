package view;

import controller.GameListener;
import model.Maze;
import model.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeVisualPanel extends JPanel {

    private final GameListener myGameListener;

    public MazeVisualPanel(final GameListener theGameListener, Maze theMaze) {
        myGameListener = theGameListener;

        setLayout(new GridLayout(theMaze.getRows(), theMaze.getCols()));

        for (int i = 0; i < theMaze.getRows(); i++) {
            for (int j = 0; j < theMaze.getCols(); j++) {
                var room = theMaze.getRoom(i, j);

                MazeVisualButton button = new MazeVisualButton(room, i, j);

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        informRoomWasClicked(theMaze, button.getRow(), button.getCol());
                        myGameListener.onRoomClicked(room);
                        updateVisualInfo(theMaze);
                    }
                });


                add(button);
            }
        }

        updateVisualInfo(theMaze);

        setVisible(true);
    }


    /**
     * Tells the maze to update the highlighting of the images.
     *
     * @param theRow the row that was clicked.
     * @param theCol the col that was clicked.
     */
    private void informRoomWasClicked(final Maze theMaze, final int theRow, final int theCol) {
        theMaze.setRoomHigLig(theRow, theCol);
    }

    /**
     * Updates the visuals of all components.
     * Must be called when any visual change occurs in the maze.
     */
    private void updateVisualInfo(Maze theMaze) {
        theMaze.updateRoomVisibility();
        Component[] components = this.getComponents();
        for (Component component : components) {
            if (component instanceof MazeVisualButton) {
                MazeVisualButton button = (MazeVisualButton) component;
                button.updateVisualImage();
            }
        }
    }
//
//    /**
//     * Gets the currently selected room.
//     *
//     * @return the currently selected room.
//     */
//    public Room getCurrentlySelectedRoom() {
//        return myMaze.getCurrentlySelectedRoom();
//    }
}
