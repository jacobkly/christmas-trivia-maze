package view;

import controller.GameListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class StatusBarPanel extends JPanel {

    public StatusBarPanel(final GameListener theGameListener, final int theHealthCount, final int theHintCount) {
//        setPreferredSize(new Dimension(500, 160));
        setBackground(Color.DARK_GRAY);
        setVisible(true);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        BufferedImage healthImage;
        BufferedImage hintImage;

        try {
            healthImage = ImageIO.read(getClass().getResource("/resources/heart.png"));
            hintImage = ImageIO.read(getClass().getResource("/resources/lightbulb.png"));
        } catch (final Exception theError) {
            throw new RuntimeException("Problem with image initialization; " + theError);
        }

        JPanel healthPanel = setupPanel("Attempts: ", healthImage, theHealthCount);
        add(healthPanel);

        JPanel hintPanel = setupPanel("Hints: ", hintImage, theHintCount);
        add(hintPanel);
    }

    private JPanel setupPanel(final String theText, final BufferedImage theImage, final int theNumImages) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());
//        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // testing panel size

        GridBagConstraints constraint = new GridBagConstraints();
        constraint.gridx = GridBagConstraints.CENTER;
        constraint.gridy = GridBagConstraints.CENTER;

        JTextArea textArea = new JTextArea(theText);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.BOLD, 25));
        panel.add(textArea, constraint);
        constraint.gridx++;

        for (int i = 0; i < theNumImages; i++) {
            JLabel imageLabel = new JLabel(new ImageIcon(
                    theImage.getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
//            imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // testing image size
            panel.add(imageLabel, constraint);

            constraint.gridx++; // move to next pos for next image
        }
        return panel;
    }
}