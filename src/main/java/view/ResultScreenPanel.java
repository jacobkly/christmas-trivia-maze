package view;

import controller.GameListener;

import javax.swing.*;
import java.awt.*;

public class ResultScreenPanel extends JPanel {

    private final static String[] myVictoryText = new String[] {
            "Ho ho ho!",
            "You've reached the North Pole!",
            "Welcome to my workshop!"
    };

    private final static String[] myVictoryStatsText = new String[] {
            "Wow, you've been a busy elf!",
            "These stats are incredible!"
    };

    private final static String[] myDefeatText = new String[] {
            "Bah! Humbag!",
            "You couldn't even answer a simple question about Christmas?",
            "What a Christmas Crook."
    };

    private final static String[] myDefeatStatsText = new String[] {
            "Well, these stats are a Christmas catastrophe.",
            "Time to hit the books and level up your holiday IQ.",
            "Or maybe just stick to easier games..."
    };

    private final GameListener myGameListener;

    private final boolean myIsVictory;

    private final JPanel myInnerPanel;

    private final JButton[] myButtons = {
            new JButton("New Game"),
            new JButton("Main Menu"),
            new JButton("Settings"),
            new JButton("Exit")
    };

    public ResultScreenPanel(final GameListener theGameListener, final boolean theIsVictory) {
        myGameListener = theGameListener;
        myIsVictory = theIsVictory;

        setSize(1214, 760);
        setLayout(new BorderLayout());
        selectBackground();

        myInnerPanel = new JPanel(new GridBagLayout());
        myInnerPanel.setBackground(new Color(0,0,0,0));
        myInnerPanel.setLayout(new GridBagLayout());

        GridBagConstraints innerConstraints = new GridBagConstraints();
        innerConstraints.insets = new Insets(10,10,10,10);
        innerConstraints.anchor = GridBagConstraints.CENTER;
        innerConstraints.gridx = 0;
        innerConstraints.gridy = 0;
        setupInnerPanel(innerConstraints);

        add(myInnerPanel, BorderLayout.EAST);
    }

    private void selectBackground() {
        if (myIsVictory) {
            setBackground(Color.BLACK); // change to victory background
        } else {
            setBackground(Color.BLACK); // change to defeat background
        }
    }

    private void setupInnerPanel(final GridBagConstraints theConstraints) {
        if (myIsVictory) {
            addText(new String[] {"Victory!"}, 55, theConstraints);
            addText(myVictoryText, 12, theConstraints);
            addText(myVictoryStatsText, 12, theConstraints);
        } else {
            addText(new String[] {"Defeat"}, 55, theConstraints);
            addText(myDefeatText, 12, theConstraints);
            addText(myDefeatStatsText, 12, theConstraints);
        }

        formatButtons();
        addButtons(theConstraints);
    }

    private void addText(final String[] theText, final int theFontSize, GridBagConstraints theConstraints) {
        JPanel textPanel = new JPanel(new GridBagLayout());
        textPanel.setBackground(new Color(0,0,0,0));

        for (String text : theText) {
            JLabel textLabel = new JLabel(text);
            textLabel.setForeground(Color.WHITE);
            textLabel.setFont(Fonts.getPixelFont(theFontSize));
            textLabel.setBackground(new Color(0,0,0,0));

            GridBagConstraints textConstraints = new GridBagConstraints();
            textConstraints.insets = new Insets(10, 10, 10, 10);
            textConstraints.anchor = GridBagConstraints.CENTER;
            textConstraints.fill = GridBagConstraints.NONE;
            textConstraints.gridx = 0;
            textConstraints.gridy = GridBagConstraints.RELATIVE;

            textPanel.add(textLabel, textConstraints);
            textConstraints.gridy++;
        }

        myInnerPanel.add(textPanel, theConstraints);
        theConstraints.gridy++;
    }

    private void formatButtons() {
        for (JButton button : myButtons) {
            button.setBackground(new Color(241, 241, 241, 175));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            button.setForeground(Color.BLACK);
            button.setFont(Fonts.getPixelFont(15));
            button.setFocusable(false);
            button.setRolloverEnabled(false);
        }
    }

    private void addButtons(final GridBagConstraints theConstraints) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 0, 0, 0));
        buttonPanel.setLayout(new GridBagLayout());

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.anchor = GridBagConstraints.WEST;
        buttonConstraints.insets = new Insets(3, 10, 3, 10);
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 0;

        for (int i = 0; i < myButtons.length; i++) {
            myButtons[i].setPreferredSize(new Dimension(400, 50));
            buttonPanel.add(myButtons[i], buttonConstraints);
            buttonConstraints.gridy++;

            if (i == 0) {
                myButtons[i].addActionListener(e -> myGameListener.startPreparation());
            } else if (i == 1) {
                myButtons[i].addActionListener(e -> myGameListener.startMainMenu());
            } else if (i == 2) {
                myButtons[i].addActionListener(e -> {});
            } else {
                myButtons[i].addActionListener(e -> System.exit(0));
            }
        }

        myInnerPanel.add(buttonPanel, theConstraints);
    }
}
