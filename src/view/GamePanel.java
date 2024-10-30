package view;

import controller.GameListener;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {


    public GamePanel(GameListener myGameListener) {

        setBackground(Color.ORANGE);
        setSize(660,660);
        setVisible(true);


    }
}
