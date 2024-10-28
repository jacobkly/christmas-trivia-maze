package view;

import controller.GameListener;

import javax.swing.*;
import java.awt.*;

public class QuestionPanel extends JPanel {
    public QuestionPanel(GameListener myGameListener) {

        setSize(400, 400);
        setBackground(Color.GREEN);
        setVisible(true);
    }
}
