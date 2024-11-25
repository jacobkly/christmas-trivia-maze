package view;

import controller.GameListener;
import model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A panel displaying the player's status, including health and hints. It also contains a button to
 * use a hint, updating the display based on the player's current state.
 *
 * @author Mathew Miller
 * @author Jacob Klymenko
 * @version 1.0
 */
public class StatusBarPanel extends JPanel {

    /** The height of the status bar panel in pixels. */
    private static final int PANEL_HEIGHT = 150;

    /** The width of the status bar panel in pixels. */
    private static final int PANEL_WIDTH = 520;

    /** The size (width and height) of the icons displayed in the status panels, in pixels. */
    private static final int ICON_SIZE = 40;

    /** The GameListener object used to interact with the game's logic. */
    private final GameListener myGameListener;

    /** The JButton used to trigger the hint action. */
    private final JButton myGetHint;

    /** The image for the player's health display. */
    private final BufferedImage myHealthImage;

    /** The image for the hint display. */
    private final BufferedImage myHintImage;

    /** The JPanel used to display the player's health status. */
    private final JPanel myHealthPanel;

    /** The JPanel used to display the player's hint status. */
    private final JPanel myHintPanel;

    /**
     * Constructs a StatusBarPanel to display the player's status, including health and hints.
     *
     * @param theGameListener the GameListener object used to interact with the game
     */
    public StatusBarPanel(final GameListener theGameListener) {
        myGameListener = theGameListener;
        setPanelDefaults();
        myHealthImage = loadImage("/statusBarFiles/heart.png");
        myHintImage = loadImage("/statusBarFiles/lightbulb.png");

        myHealthPanel = createStatusPanelContainer();
        myHintPanel = createStatusPanelContainer();

        myGetHint = createHintButton();

        assemblePanel();
    }

    /**
     * Sets default configurations for the panel.
     */
    private void setPanelDefaults() {
        setBackground(Color.BLACK);
        setBorder(new RoundedBorder(40));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setVisible(true);
    }

    /**
     * Loads an image from the given path.
     *
     * @param thePath the path to the image resource
     * @return the loaded BufferedImage
     */
    private BufferedImage loadImage(final String thePath) {
        try {
            return ImageIO.read(getClass().getResource(thePath));
        } catch (final Exception theError) {
            throw new RuntimeException("Problem with image initialization; " + theError);
        }
    }

    /**
     * Creates a reusable container for status panels.
     *
     * @return a JPanel for displaying status elements
     */
    private JPanel createStatusPanelContainer() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        return panel;
    }

    /**
     * Creates the hint button with all necessary configurations.
     *
     * @return the configured JButton
     */
    private JButton createHintButton() {
        JButton hintButton = new JButton("Open Gift");
        hintButton.addActionListener(e -> myGameListener.useHint());
        hintButton.setBackground(Color.BLACK);
        hintButton.setFont(Fonts.getPixelFont(12));
        hintButton.setForeground(Color.WHITE);
        hintButton.setBorder(new RoundedBorder(20, new Insets(5, 5, 5, 5)));
        hintButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return hintButton;
    }

    /**
     * Assembles all components into the main panel.
     */
    private void assemblePanel() {
        add(Box.createVerticalGlue());
        add(myHealthPanel);
        add(Box.createVerticalGlue());
        add(myHintPanel);
        add(Box.createVerticalGlue());
        add(myGetHint);
        add(Box.createVerticalGlue());
    }

    /**
     * Creates a JPanel with text and images displaying the player's status (e.g., health or hints).
     *
     * @param theText the label text to display
     * @param theImage the image to display next to the text
     * @param theNumImages the number of images to display
     * @return the JPanel containing the text and images
     */
    private JPanel createStatusPanel(final String theText,
                                     final BufferedImage theImage,
                                     final int theNumImages) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, ICON_SIZE));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraint = new GridBagConstraints();
        constraint.gridx = 0;

        JTextArea textArea = new JTextArea(theText);
        textArea.setEditable(false);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(Fonts.getPixelFont(16));
        panel.add(textArea, constraint);
        constraint.gridx++;

        for (int i = 0; i < theNumImages; i++) {
            JLabel imageLabel = new JLabel(new ImageIcon(
                    theImage.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_DEFAULT)));
            panel.add(imageLabel, constraint);
            constraint.gridx++;
        }
        return panel;
    }

    /**
     * Updates the status bar with the player's current health and hint values.
     *
     * @param thePlayer the Player object whose status will be displayed
     */
    public void setPlayerInfo(final Player thePlayer) {
        updateStatusPanel(myHealthPanel, "Life: ", myHealthImage, thePlayer.getHealthCount());
        updateStatusPanel(myHintPanel, "Gifts: ", myHintImage, thePlayer.getHints());
        myGetHint.setEnabled(thePlayer.getHints() > 0);
    }

    /**
     * Updates a status panel with new data.
     *
     * @param thePanel the panel to update
     * @param theLabel the label text
     * @param theImage the image to display
     * @param theCount the number of images to display
     */
    private void updateStatusPanel(final JPanel thePanel, final String theLabel,
                                   final BufferedImage theImage, final int theCount) {
        thePanel.removeAll();
        thePanel.add(createStatusPanel(theLabel, theImage, theCount));
        thePanel.repaint();
    }
}