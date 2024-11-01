package view;

import controller.GameListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.GridBagConstraints.RELATIVE;

public class KeyPanel extends JPanel {

    private final static String[] KEY_DESCRIPTIONS = {"Locked Room: ", "Health Count: ", "Hint: ",
                                                        "Undiscovered Room: ", "Discovered Room: "};

    public KeyPanel(GameListener myGameListener) {

        setBackground(Color.BLACK);
        setBorder(new RoundedBorder(40));
        setVisible(true);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 0;

        BufferedImage[] discRoomImages = new BufferedImage[3];
        BufferedImage[] keyImages = new BufferedImage[4];

        try {
            keyImages[0] = ImageIO.read(getClass().getResource("/roomFiles/fillRoom/lockFillRoom.png"));
            keyImages[1] = ImageIO.read(getClass().getResource("/statusBarFiles/heart.png"));
            keyImages[2] = ImageIO.read(getClass().getResource("/statusBarFiles/lightbulb.png"));
            keyImages[3] = ImageIO.read(getClass().getResource("/roomFiles/fillRoom/mystFillRoom.png"));

            discRoomImages[0] = ImageIO.read(getClass().getResource("/roomFiles/fillRoom/lndscFillRoom.png"));
            discRoomImages[1] = ImageIO.read(getClass().getResource("/roomFiles/fillRoom/santaFillRoom.png"));
            discRoomImages[2] = ImageIO.read(getClass().getResource("/roomFiles/fillRoom/treeFillRoom.png"));
        } catch (final Exception theError) {
            throw new RuntimeException("Problem with image initialization; " + theError);
        }

        setupPanel(keyImages, discRoomImages, constraints);

    }

    private void setupPanel(final BufferedImage[] theKeyImages, final BufferedImage[] theDiscRoomImages,
                            final GridBagConstraints theConstraints) {
        int colPerRow = 4;
        int colCount = 0;

        for (int i = 0; i < KEY_DESCRIPTIONS.length - 1; i++) {
            JLabel textArea = new JLabel(KEY_DESCRIPTIONS[i]);
            textArea.setBackground(Color.BLACK);
            textArea.setForeground(Color.WHITE);
            textArea.setFont(Fonts.getPixelFont(16));
            theConstraints.gridx = colCount;
            add(textArea, theConstraints);
            colCount++;

            JLabel imageLabel = new JLabel(new ImageIcon(theKeyImages[i].
                    getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
            theConstraints.gridx = colCount;
            add(imageLabel, theConstraints);
            colCount++;

            if (colCount >= colPerRow) {
                colCount = 0;
                theConstraints.gridy++;
            }
        }

        if (colCount != 0) {
            colCount = 0;
            theConstraints.gridy++;
        }

        JLabel textArea = new JLabel(KEY_DESCRIPTIONS[KEY_DESCRIPTIONS.length - 1]);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(Fonts.getPixelFont(16));
        theConstraints.gridx = colCount;
        add(textArea, theConstraints);
        colCount++;

        for (int i = 0; i < theDiscRoomImages.length; i++) {
            JLabel imageLabel = new JLabel(new ImageIcon(theDiscRoomImages[i].
                    getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
            theConstraints.gridx = colCount;
            add(imageLabel, theConstraints);
            colCount++;

            if (colCount >= colPerRow) {
                colCount = 0;
                theConstraints.gridy++;
            }
        }
    }
}
