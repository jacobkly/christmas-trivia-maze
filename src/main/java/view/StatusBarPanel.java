package view;

import controller.GameListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StatusBarPanel extends JPanel {

    public StatusBarPanel(final GameListener theGameListener, final int theHealthCount, final int theHintCount) {

        setBackground(Color.BLACK);
        setBorder(new RoundedBorder(40));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMinimumSize(new Dimension(520, 150));
        setVisible(true);

        BufferedImage healthImage;
        BufferedImage hintImage;

        try {
            healthImage = ImageIO.read(getClass().getResource("/statusBarFiles/heart.png"));
            hintImage = ImageIO.read(getClass().getResource("/statusBarFiles/lightbulb.png"));
        } catch (final Exception theError) {
            throw new RuntimeException("Problem with image initialization; " + theError);
        }

        add(Box.createVerticalGlue());

        JPanel healthPanel = setupPanel("Life: ", healthImage, theHealthCount);
        add(healthPanel);

        add(Box.createVerticalGlue());

        JPanel hintPanel = setupPanel("Hints: ", hintImage, theHintCount);
        add(hintPanel);

        add(Box.createVerticalGlue());

        JButton getHint = new JButton("Hint");
        getHint.setBackground(Color.BLACK);
        getHint.setFont(Fonts.getPixelFont(12));
        getHint.setForeground(Color.WHITE);
        getHint.setBorder(new RoundedBorder(20, new Insets(5,5,5,5)));
        getHint.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(getHint);

        add(Box.createVerticalGlue());
    }

    private JPanel setupPanel(
            final String theText,
            final BufferedImage theImage,
            final int theNumImages
    ) {
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

            constraint.gridx++; // move to next pos for next image
        }
        return panel;
    }
}