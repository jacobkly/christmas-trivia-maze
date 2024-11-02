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
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        add(myQuestionPanel, c);

        c2.gridx = 1;
        c2.gridy = 1;
        c2.anchor = SOUTHEAST;
        c2.insets = new Insets(5, 5, 5, 5);
        c2.weightx = 1.0;
        c2.weighty = 1.0;
        c2.fill = GridBagConstraints.BOTH;
        add(myStatusBarPanel, c2);

        c3.gridx = 0;
        c3.gridy = 1;
        c3.anchor = SOUTHWEST;
        c3.insets = new Insets(5, 5, 5, 5);
        c3.weightx = 1.0;
        c3.weighty = 1.0;
        c3.fill = GridBagConstraints.BOTH;
        add(myKeyPanel, c3);

        c4.gridx = 0;
        c4.gridy = 0;
        c4.anchor = NORTHWEST;
        c4.insets = new Insets(5, 5, 5, 5);
        c4.weightx = 1.0;
        c4.weighty = 1.0;
        c4.fill = GridBagConstraints.BOTH;
        add(myGamePanel, c4);




    }
}
