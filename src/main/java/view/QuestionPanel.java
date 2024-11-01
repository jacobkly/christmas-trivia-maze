package view;

import controller.GameListener;

import javax.swing.*;
import java.awt.*;

public class QuestionPanel extends JPanel {
    public QuestionPanel(GameListener myGameListener) {

        setSize(400, 400);
        setBorder(new RoundedBorder(40));
        setBackground(Color.BLACK);
        setVisible(true);
    }
}
