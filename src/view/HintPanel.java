package view;

import controller.GameListener;

import javax.swing.*;
import java.awt.*;

public class HintPanel extends JPanel {

    public HintPanel(GameListener myGameListener) {

        setSize(400, 100);
        setBackground(Color.DARK_GRAY);
        setVisible(true);
    }
}
