package view;

import controller.GameListener;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {


    public GamePanel(GameListener myGameListener) {

        setBackground(Color.BLACK);
        setBorder(new RoundedBorder(40));
        add(new MazeVisualPanel(5, 8));
        setVisible(true);


    }




}
