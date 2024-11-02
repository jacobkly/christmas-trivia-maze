package view;

import model.Maze;
import model.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeVisualPanel extends JPanel {

    private Maze myMaze;


    public MazeVisualPanel(final int theNumRows, final int theNumCols) {


        // here make start/end
        int startRow = 1;
        int startCol = 1;
        int endRow = 3;
        int endCol = 4;

        myMaze = new Maze(theNumRows, theNumCols, startRow, startCol, endRow, endCol);

        setLayout(new GridLayout(theNumRows, theNumCols));

        for(int i = 0; i < theNumRows; i++) {
            for(int j = 0; j < theNumCols; j++) {
                MazeVisualButton button = new MazeVisualButton(myMaze.getRoom(i, j), i, j);

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        informRoomWasClicked(button.getRow(), button.getCol());
                        updateVisualInfo();
                    }
                });


                add(button);
            }
        }

        updateVisualInfo();

        setVisible(true);
    }



    /**
     * Tells the maze to update the highlighting of the images.
     *
     * @param theRow the row that was clicked.
     * @param theCol the col that was clicked.
     */
    private void informRoomWasClicked(final int theRow, final int theCol) {
        myMaze.setRoomHigLig(theRow, theCol);
    }

    /**
     * Updates the visuals of all components.
     * Must be called when any visual change occurs in the maze.
     */
    private void updateVisualInfo() {
        myMaze.updateRoomVisibility();
        Component[] components = this.getComponents();
        for (Component component : components) {
            if (component instanceof MazeVisualButton) {
                MazeVisualButton button = (MazeVisualButton) component;
                button.updateVisualImage();
            }
        }
    }

    /**
     * Gets the currently selected room.
     *
     * @return the currently selected room.
     */
    public Room getCurrentlySelectedRoom() {
        return myMaze.getCurrentlySelectedRoom();
    }
}
