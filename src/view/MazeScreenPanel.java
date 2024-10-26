package view;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.*;

public class MazeScreenPanel extends JPanel {

    public MazeScreenPanel() {

        setSize(1000, 1000);
        setLayout(new GridBagLayout());
        setBackground(Color.RED);
        setVisible(true);

        GridBagConstraints c = new GridBagConstraints();
        GridBagConstraints c2 = new GridBagConstraints();

        QuestionPanel myQuestionPanel = new QuestionPanel();
        ResultScreenPanel myResultScreenPanel = new ResultScreenPanel();
        HintPanel myHintPanel = new HintPanel();


        c.gridx = 1;
        c.gridy = 0;
        c.anchor = NORTHEAST;
        c.insets = new Insets(5, 0, 5, 5);
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.ipadx = 300;
        c.ipady = 500;
        add(myQuestionPanel, c);

        c2.gridx = 1;
        c2.gridy = 1;
        c2.anchor = SOUTHEAST;
        c2.insets = new Insets(0, 0, 5, 5);
        c2.weightx = 1.0;
        c2.weighty = 1.0;
        c2.ipadx = 300;
        c2.ipady = 500;
        add(myHintPanel, c2);


    }
}
