package view;

import model.Maze;
import model.Room;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MazeTestPanel extends JPanel {

    private Maze myMaze;
    private MazeVisualButton mySelectedButton = null;


    public MazeTestPanel(final int theNumRows, final int theNumCols) {


        // here make start/end
        int startRow = 2;
        int startCol = 3;
        int endRow = 3;
        int endCol = 4;

        myMaze = new Maze(theNumRows, theNumCols, startRow, startCol, endRow, endCol);

        setLayout(new GridLayout(theNumRows, theNumCols));

        for(int i = 0; i < theNumRows; i++) {
            for(int j = 0; j < theNumCols; j++) {
                MazeVisualButton button = new MazeVisualButton(myMaze.getRoom(i, j), i, j);


                if(i == startRow && j == startCol) {
                    mySelectedButton = button;
                }

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        informRoomWasClicked(button.getRow(), button.getCol());
                        mySelectedButton.updateVisualImage();

                        button.updateVisualImage();

                        mySelectedButton = button;
                    }
                });


                add(button);
            }
        }


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
     */
    private void updateVisualInfo() {
        Component[] components = this.getComponents();
        for (Component component : components) {
            if (component instanceof MazeVisualButton) {
                MazeVisualButton button = (MazeVisualButton) component;
                button.updateVisualImage();
            }
        }
    }
}
