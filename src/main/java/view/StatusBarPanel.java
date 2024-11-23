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

public class StatusBarPanel extends JPanel {


    private final GameListener myGameListener;
    private final JButton myGetHint;
    private final BufferedImage healthImage;
    private final BufferedImage hintImage;
    private final JPanel myHealthPanel;
    private final JPanel myHintPanel;

    public StatusBarPanel(final GameListener theGameListener) {

        myGameListener = theGameListener;
        setBackground(Color.BLACK);
        setBorder(new RoundedBorder(40));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMinimumSize(new Dimension(520, 150));
        setVisible(true);



        try {
            healthImage = ImageIO.read(getClass().getResource("/statusBarFiles/heart.png"));
            hintImage = ImageIO.read(getClass().getResource("/statusBarFiles/lightbulb.png"));
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

        myGetHint = new JButton("Open Gift");
        myGetHint.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                myGameListener.useHint();
            }
        });
        myGetHint.setBackground(Color.BLACK);
        myGetHint.setFont(Fonts.getPixelFont(12));
        myGetHint.setForeground(Color.WHITE);
        myGetHint.setBorder(new RoundedBorder(20, new Insets(5,5,5,5)));
        myGetHint.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(myGetHint);

        add(Box.createVerticalGlue());
    }

    private JPanel createStatusPanel(
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

    public void setPlayer(Player thePlayer) {
        myHealthPanel.removeAll();
        myHintPanel.removeAll();
        myGetHint.setEnabled(thePlayer.getHints() > 0);
        JPanel healthIconPanel = createStatusPanel("Life: ", healthImage, thePlayer.getHealthCount());
        myHealthPanel.add(healthIconPanel);
        myHealthPanel.repaint();

        JPanel hintIconPanel = createStatusPanel("Gifts: ", hintImage, thePlayer.getHints());
        myHintPanel.add(hintIconPanel);
        myHintPanel.repaint();
    }
}