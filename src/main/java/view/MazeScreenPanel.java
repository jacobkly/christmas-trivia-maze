package view;

import controller.GameListener;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.*;

public class MazeScreenPanel extends JPanel {

    public MazeScreenPanel(GameListener myGameListener) {

        setSize(1200, 700 );
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        setVisible(false);

        GridBagConstraints c = new GridBagConstraints();
        GridBagConstraints c2 = new GridBagConstraints();
        GridBagConstraints c3 = new GridBagConstraints();
        GridBagConstraints c4 = new GridBagConstraints();

        GamePanel myGamePanel = new GamePanel(myGameListener);
        QuestionPanel myQuestionPanel = new QuestionPanel(myGameListener);
        StatusBarPanel myStatusBarPanel = new StatusBarPanel(myGameListener, 5, 3);
        KeyPanel myKeyPanel = new KeyPanel(myGameListener);


        c.gridx = 1;
        c.gridy = 0;
        c.anchor = NORTHEAST;
        c.insets = new Insets(10, 0, 10, 10);
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.ipadx = 600;
        c.ipady = 800;
        add(myQuestionPanel, c);

        c2.gridx = 1;
        c2.gridy = 1;
        c2.anchor = SOUTHEAST;
        c2.insets = new Insets(0, 0, 10, 10);
        c2.weightx = 1.0;
        c2.weighty = 1.0;
        c2.ipadx = 600;
        c2.ipady = 400;
        add(myStatusBarPanel, c2);

        c3.gridx = 0;
        c3.gridy = 1;
        c3.anchor = SOUTHWEST;
        c3.insets = new Insets(0, 10, 10, 10);
        c3.weightx = 1.0;
        c3.weighty = 1.0;
        c3.ipadx = 700;
        c3.ipady = 105;
        add(myKeyPanel, c3);

        c4.gridx = 0;
        c4.gridy = 0;
        c4.anchor = NORTHWEST;
        c4.insets = new Insets(10, 10, 10, 10);
        c4.weightx = 1.0;
        c4.weighty = 1.0;
        c4.ipadx = 780;
        c4.ipady = 800;
        add(myGamePanel, c4);




    }
}
