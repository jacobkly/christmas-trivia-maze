package view;

import controller.GameListener;
import controller.MusicController;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ResultScreenPanel extends JPanel {

    private final static String[] myVictoryText = new String[] {
            "Ho ho ho!",
            "You've reached the North Pole.",
            "Welcome to my workshop!"
    };

    private final static String[] myVictoryStatsText = new String[] {
            "Wow, you've been a busy elf!",
            "These stats are incredible!"
    };

    private final static String[] myDefeatText = new String[] {
            "Woah...",
            "Looks like something went wrong.",
            "Better luck next time!"
    };

    private final static String[] myDefeatStatsText = new String[] {
            "Oh dear, these stats look frosty!",
            "It's time to brush up your trivia."
    };

    private final GameListener myGameListener;

    private final MusicController myMusicController;

    private final VolumeSliderPanel myVolumeSliderPanel;

    private final JPanel myInnerPanel;

    private final GridBagConstraints myInnerPanelConstraints;

    private final JButton[] myButtons = {
            new JButton("Main Menu"),
            new JButton("Settings"),
            new JButton("Exit")
    };

    /** True is victory and false is defeat. */
    private boolean myResult;

    public ResultScreenPanel(final GameListener theGameListener, final MusicController theMusicController,
                             final VolumeSliderPanel theVolumeSliderPanel) {
        myGameListener = theGameListener;
        myMusicController = theMusicController;
        myVolumeSliderPanel = theVolumeSliderPanel;
        myResult = false;

        setSize(1214, 760);
        setLayout(new BorderLayout());

        myInnerPanel = new JPanel(new GridBagLayout());
        myInnerPanel.setBackground(new Color(0,0,0,0));
        myInnerPanel.setLayout(new GridBagLayout());

        myInnerPanelConstraints = new GridBagConstraints();
        myInnerPanelConstraints.insets = new Insets(10,10,10,80);
        myInnerPanelConstraints.anchor = GridBagConstraints.CENTER;
        myInnerPanelConstraints.gridx = 0;
        myInnerPanelConstraints.gridy = 0;

        add(myInnerPanel, BorderLayout.EAST);
    }

    public void updatePanel(final boolean theResult) {
        myResult = theResult;

        myInnerPanel.removeAll();
        myInnerPanel.revalidate();
        myInnerPanel.repaint();

        setupInnerPanel();
    }

    private void setupInnerPanel() {
        String[] playerStats = myGameListener.getPlayerStatistics();
        if (myResult) {
            addText(new String[] {"Victory!"}, 55);
            addText(myVictoryText, 15);
            addText(playerStats, 12);
            addText(myVictoryStatsText, 15);
        } else {
            addText(new String[] {"Defeat"}, 55);
            addText(myDefeatText, 15);
            addText(playerStats, 12);
            addText(myDefeatStatsText, 15);
        }

        formatButtons();
        addButtons();
    }

    private void addText(final String[] theText, final int theFontSize) {
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
        myInnerPanel.add(textPanel, myInnerPanelConstraints);
        myInnerPanelConstraints.gridy++;
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

    private void addButtons() {
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

            // repaint() is used to bring back transparency to button after a user click
            if (i == 0) {
                myButtons[i].addActionListener(e -> { myGameListener.startMainMenu(); repaint(); });
            } else if (i == 1) {
                myButtons[i].addActionListener(e -> {
                    myVolumeSliderPanel.showDialog(ResultScreenPanel.this, "Settings");
                    repaint();
                });
            } else {
                myButtons[i].addActionListener(e -> System.exit(0));
            }
        }
        myInnerPanel.add(buttonPanel, myInnerPanelConstraints);
    }

    @Override
    protected void paintComponent(final Graphics theGraphics) {
        ImageIcon icon;
        if (myResult) {
            icon = new ImageIcon(Objects.requireNonNull(
                    getClass().getResource("/menuScreens/winScreen96x54.png")));
        } else {
            icon = new ImageIcon(Objects.requireNonNull(
                    getClass().getResource("/menuScreens/lossScreen_96x54.png")));
        }

        Image myImage = new ImageIcon(String.valueOf(icon)).getImage();
        super.paintComponent(theGraphics);

        if (myImage != null) {
            theGraphics.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}
