package view;

import controller.GameListener;

import javax.swing.*;
import java.awt.*;

public class KeyPanel extends JPanel {

    public KeyPanel(GameListener myGameListener) {

        setSize(600, 300);
        setBackground(Color.CYAN);
        setVisible(true);
    }
}
