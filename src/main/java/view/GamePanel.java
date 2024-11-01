package view;

import controller.GameListener;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {


    public GamePanel(GameListener myGameListener) {

        setBackground(Color.BLACK);
        setBorder(new RoundedBorder(40));
        setVisible(true);


    }
}
