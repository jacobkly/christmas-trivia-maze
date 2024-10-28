package view;

import controller.GameListener;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.*;

public class MazeScreenPanel extends JPanel {

    public MazeScreenPanel(GameListener myGameListener) {

        setSize(1200, 700 );
        setLayout(new GridBagLayout());
        setBackground(Color.RED);
        setVisible(false);

        GridBagConstraints c = new GridBagConstraints();
        GridBagConstraints c2 = new GridBagConstraints();
        GridBagConstraints c3 = new GridBagConstraints();

        QuestionPanel myQuestionPanel = new QuestionPanel(myGameListener);
        ResultScreenPanel myResultScreenPanel = new ResultScreenPanel();
        HintPanel myHintPanel = new HintPanel(myGameListener);
        KeyPanel myKeyPanel = new KeyPanel(myGameListener);


        c.gridx = 1;
        c.gridy = 0;
        c.anchor = NORTHEAST;
        c.insets = new Insets(10, 0, 0, 10);
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.ipadx = 600;
        c.ipady = 700;
        add(myQuestionPanel, c);

        c2.gridx = 1;
        c2.gridy = 1;
        c2.anchor = SOUTHEAST;
        c2.insets = new Insets(10, 0, 10, 10);
        c2.weightx = 1.0;
        c2.weighty = 1.0;
        c2.ipadx = 600;
        c2.ipady = 400;
        add(myHintPanel, c2);

        c3.gridx = 0;
        c3.gridy = 1;
        c3.anchor = SOUTHWEST;
        c3.insets = new Insets(0, 10, 10, 10);
        c3.weightx = 1.0;
        c3.weighty = 1.0;
        c3.ipadx = 1000;
        c3.ipady = 200;
        add(myKeyPanel, c3);


    }
}
