package view;

import controller.GameListener;

import javax.swing.*;
import java.awt.*;

public class HintPanel extends JPanel {


    public HintPanel(GameListener myGameListener) {

        setBackground(Color.BLACK);
        setBorder(new RoundedBorder(40));
        setVisible(true);


    }
}
