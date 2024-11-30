package view;

import controller.GameListener;
import model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

/**
 * A JPanel subclass that displays the result screen after the game ends, showing either a victory
 * or defeat message along with relevant statistics.
 *
 * @author Jacob Klymenko
 * @version 1.0
 */
public class ResultScreenPanel extends JPanel {

    /**
     * Array of text for the victory message.
     */
    private final static String[] VICTORY_TEXT = new String[]{
            "Ho ho ho!",
            "You've reached the North Pole.",
            "Welcome to my workshop!"
    };

    /**
     * Array of text for the victory statistics message.
     */
    private final static String[] VICTORY_STATS_TEXT = new String[]{
            "Wow, you've been a busy elf!",
            "These stats are incredible!"
    };

    /**
     * Array of text for the defeat message.
     */
    private final static String[] DEFEAT_TEXT = new String[]{
            "Woah...",
            "Looks like something went wrong.",
            "Better luck next time!"
    };

    /**
     * Array of text for the defeat statistics message.
     */
    private final static String[] DEFEAT_STATS_TEXT = new String[]{
            "Oh dear, these stats look frosty!",
            "It's time to brush up your trivia."
    };

    /**
     * The game listener responsible for game-related events.
     */
    private final GameListener myGameListener;

    /**
     * The volume slider panel used for adjusting volume settings.
     */
    private final VolumeSliderPanel myVolumeSliderPanel;

    /**
     * The inner panel that holds the result text and buttons.
     */
    private final JPanel myInnerPanel;

    /**
     * The GridBagConstraints used for positioning components in the inner panel.
     */
    private final GridBagConstraints myInnerPanelConstraints;

    /**
     * Array of buttons for the result screen actions.
     */
    private final JButton[] myButtons = {
            new JButton("Main Menu"),
            new JButton("Settings"),
            new JButton("Exit")
    };

    /**
     * A boolean indicating the result of the game: true for victory, false for defeat.
     */
    private boolean myResult;

    /**
     * Constructs a ResultScreenPanel with the specified game listener, music controller,
     * and volume slider panel.
     *
     * @param theGameListener      the listener for game events
     * @param theVolumeSliderPanel the panel to control volume settings
     */
    public ResultScreenPanel(final GameListener theGameListener,
                             final VolumeSliderPanel theVolumeSliderPanel) {
        myGameListener = theGameListener;
        myVolumeSliderPanel = theVolumeSliderPanel;
        myResult = false;

        setSize(1214, 760);
        setLayout(new BorderLayout());

        myInnerPanel = new JPanel(new GridBagLayout());
        myInnerPanel.setBackground(new Color(0, 0, 0, 0));
        myInnerPanel.setLayout(new GridBagLayout());

        myInnerPanelConstraints = new GridBagConstraints();
        myInnerPanelConstraints.insets = new Insets(10, 10, 10, 80);
        myInnerPanelConstraints.anchor = GridBagConstraints.CENTER;
        myInnerPanelConstraints.gridx = 0;
        myInnerPanelConstraints.gridy = 0;

        add(myInnerPanel, BorderLayout.EAST);
    }

    /**
     * Updates the panel to reflect the result of the game session.
     *
     * @param theResult Indicates if this was a win or a loss.
     * @param thePlayerStatistics Strings describing various statistics about the players game session.
     */
    public void setResult(final boolean theResult, final String[] thePlayerStatistics) {
        myResult = theResult;

        myInnerPanel.removeAll();
        myInnerPanel.revalidate();
        myInnerPanel.repaint();

        setupInnerPanel(thePlayerStatistics);
    }

    /**
     * Sets up the inner panel with the appropriate text and statistics based on the result.
     *
     * @param thePlayerStatistics Strings describing various statistics about the players game session.
     */
    private void setupInnerPanel(final String[] thePlayerStatistics) {
        if (myResult) {
            addText(new String[]{"Victory!"}, 55);
            addText(VICTORY_TEXT, 15);
            addText(thePlayerStatistics, 12);
            addText(VICTORY_STATS_TEXT, 15);
        } else {
            addText(new String[]{"Defeat"}, 55);
            addText(DEFEAT_TEXT, 15);
            addText(thePlayerStatistics, 12);
            addText(DEFEAT_STATS_TEXT, 15);
        }

        formatButtons();
        addButtons();
    }

    /**
     * Adds a set of text labels to the inner panel.
     *
     * @param theText     the array of text to display
     * @param theFontSize the font size to use for the text
     */
    private void addText(final String[] theText, final int theFontSize) {
        JPanel textPanel = new JPanel(new GridBagLayout());
        textPanel.setBackground(new Color(0, 0, 0, 0));

        for (String text : theText) {
            JLabel textLabel = new JLabel(text);
            textLabel.setForeground(Color.WHITE);
            textLabel.setFont(Fonts.getPixelFont(theFontSize));
            textLabel.setBackground(new Color(0, 0, 0, 0));

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

    /**
     * Formats the buttons on the result screen (Main Menu, Settings, Exit).
     */
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

    /**
     * Adds the action buttons (Main Menu, Settings, Exit) to the inner panel.
     */
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
                myButtons[i].addActionListener(e -> {
                    myGameListener.startMainMenu();
                    repaint();
                });
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

    /**
     * Paints the background of the result screen with an appropriate image based on the result.
     *
     * @param theGraphics the graphics object to paint on
     */
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        InputStream backgroundImageStream;
        if (myResult) {
            backgroundImageStream = getClass().getResourceAsStream("/menuScreens/winScreen96x54.png");
        } else {
            backgroundImageStream = getClass().getResourceAsStream("/menuScreens/lossScreen_96x54.png");
        }

        if (backgroundImageStream != null) {
            try {
                Image image = ImageIO.read(backgroundImageStream);
                theGraphics.drawImage(image, 0, 0, 1214, 720, null);
                backgroundImageStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
