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
        setBackground(Color.BLACK);
        setBorder(new RoundedBorder(40));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMinimumSize(new Dimension(520, 150));
        setVisible(true);

        try {
            myHealthImage = ImageIO.read(getClass().getResource("/statusBarFiles/heart.png"));
            myHintImage = ImageIO.read(getClass().getResource("/statusBarFiles/lightbulb.png"));
        } catch (final Exception theError) {
            throw new RuntimeException("Problem with image initialization; " + theError);
        }

        add(Box.createVerticalGlue());
        myHealthPanel = new JPanel();
        myHealthPanel.setBackground(Color.BLACK);
        add(myHealthPanel);

        add(Box.createVerticalGlue());
        myHintPanel = new JPanel();
        myHintPanel.setBackground(Color.BLACK);
        add(myHintPanel);

        add(Box.createVerticalGlue());

        myGetHint = new JButton("Hint");
        myGetHint.addActionListener(e -> myGameListener.useHint());
        myGetHint.setBackground(Color.BLACK);
        myGetHint.setFont(Fonts.getPixelFont(12));
        myGetHint.setForeground(Color.WHITE);
        myGetHint.setBorder(new RoundedBorder(20, new Insets(5,5,5,5)));
        myGetHint.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraint = new GridBagConstraints();
        constraint.gridx = 0;
        constraint.gridy = 0;

        JTextArea textArea = new JTextArea(theText);
        textArea.setEditable(false);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(Fonts.getPixelFont(16));
        panel.add(textArea, constraint);
        constraint.gridx++;

        for (int i = 0; i < theNumImages; i++) {
            JLabel imageLabel = new JLabel(new ImageIcon(
                    theImage.getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
            panel.add(imageLabel, constraint);
            constraint.gridx++; // move to next position for the next image
        }
        return panel;
    }

    /**
     * Updates the status bar with the player's current health and hint values.
     *
     * @param thePlayer the Player object whose status will be displayed
     */
    public void setPlayer(Player thePlayer) {
        myHealthPanel.removeAll();
        myHintPanel.removeAll();
        myGetHint.setEnabled(thePlayer.getHints() > 0);
        JPanel healthIconPanel = createStatusPanel("Life: ", myHealthImage,
                                                    thePlayer.getHealthCount());
        myHealthPanel.add(healthIconPanel);
        myHealthPanel.repaint();

        JPanel hintIconPanel = createStatusPanel("Hints: ", myHintImage,
                                                    thePlayer.getHints());
        myHintPanel.add(hintIconPanel);
        myHintPanel.repaint();
    }
}