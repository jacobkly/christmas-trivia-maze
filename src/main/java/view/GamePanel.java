package view;

import controller.GameListener;
import model.Maze;
import model.Room;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {


    public GamePanel(GameListener myGameListener) {

        setBackground(Color.BLACK);
        setBorder(new RoundedBorder(40));
        add(new MazeTestPanel(5, 7));
        setVisible(true);


    }




}
