package view;

import controller.GameListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static java.awt.GridBagConstraints.RELATIVE;

/**
 * Represents a key panel in the user interface that displays various room types and status
 * indicators with corresponding images. Extends {@link JPanel} for custom layout and rendering of
 * images and text.
 *
 * @author Jacob Klymenko
 * @author Mathew Miller
 * @version 1.0
 */
public class KeyPanel extends JPanel {

    /** An array of descriptions for the different keys and room types. */
    private static final String[] KEY_DESCRIPTIONS = {
            "Locked Room: ",
            "Health Count: ",
            "Hint: ",
            "Undiscovered Room: ",
            "Discovered Room: "};

    /** The number of columns per row in the panel layout. */
    private static final int COLUMNS_PER_ROW = 4;

    /** The size (width and height) of the icons displayed in the key panel. */
    private static final int ICON_SIZE = 40;

    /**
     * Constructs a KeyPanel and initializes its layout, images, and appearance.
     */
    public KeyPanel() {
        setBackground(Color.BLACK);
        setBorder(new RoundedBorder(40));
        setVisible(true);
        setLayout(new GridBagLayout());

        BufferedImage[] keyImages = loadKeyImages();
        BufferedImage[] discRoomImages = loadDiscoveredRoomImages();
        setupPanel(keyImages, discRoomImages);
    }

    /**
     * Loads the key images for the panel.
     *
     * @return an array of key images.
     */
    private BufferedImage[] loadKeyImages() {
        BufferedImage[] keyImages = new BufferedImage[4];
        try {
            keyImages[0] = ImageIO.read(Objects.requireNonNull(getClass().getResource(
                    "/roomFiles/fillRoom/lockFillRoom.png")));
            keyImages[1] = ImageIO.read(Objects.requireNonNull(getClass().getResource(
                    "/statusBarFiles/heart.png")));
            keyImages[2] = ImageIO.read(Objects.requireNonNull(getClass().getResource(
                    "/statusBarFiles/lightbulb.png")));
            keyImages[3] = ImageIO.read(Objects.requireNonNull(getClass().getResource(
                    "/roomFiles/fillRoom/mystFillRoom.png")));
        } catch (final Exception theError) {
            throw new RuntimeException("Problem with key image initialization: " + theError);
        }
        return keyImages;
    }

    /**
     * Loads the discovered room images for the panel.
     *
     * @return an array of discovered room images.
     */
    private BufferedImage[] loadDiscoveredRoomImages() {
        BufferedImage[] discRoomImages = new BufferedImage[3];
        try {
            discRoomImages[0] = ImageIO.read(Objects.requireNonNull(getClass().getResource(
                    "/roomFiles/fillRoom/lndscFillRoom.png")));
            discRoomImages[1] = ImageIO.read(Objects.requireNonNull(getClass().getResource(
                    "/roomFiles/fillRoom/santaFillRoom.png")));
            discRoomImages[2] = ImageIO.read(Objects.requireNonNull(getClass().getResource(
                    "/roomFiles/fillRoom/treeFillRoom.png")));
        } catch (final Exception theError) {
            throw new RuntimeException("Problem with discovered room image initialization: " + theError);
        }
        return discRoomImages;
    }

    /**
     * Sets up the panel by adding key images and descriptions with grid constraints.
     *
     * @param theKeyImages an array of key images to display.
     * @param theDiscRoomImages an array of discovered room images to display.
     */
    private void setupPanel(final BufferedImage[] theKeyImages,
                            final BufferedImage[] theDiscRoomImages) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 0;

        addKeyDescriptionsAndImages(theKeyImages, constraints);

        constraints.gridy++;
        constraints.gridx = 0;

        addRoomDescriptionAndImages(theDiscRoomImages, constraints);
    }

    /**
     * Adds key descriptions and their corresponding images to the panel.
     *
     * @param theKeyImages an array of key images to display.
     * @param theConstraints the grid bag constraints used for layout.
     */
    private void addKeyDescriptionsAndImages(final BufferedImage[] theKeyImages,
                                          final GridBagConstraints theConstraints) {
        int colCount = 0;

        for (int i = 0; i < KEY_DESCRIPTIONS.length - 1; i++) {
            addDescriptionWithImage(KEY_DESCRIPTIONS[i], theKeyImages[i], theConstraints, colCount);
            colCount += 2; // Increment by 2 for description and image columns.

            if (colCount >= COLUMNS_PER_ROW) {
                colCount = 0;
                theConstraints.gridy++;
            }
        }
    }

    /**
     * Adds the discovered room description and images to the panel.
     *
     * @param theDiscRoomImages an array of discovered room images to display.
     * @param theConstraints the grid bag constraints used for layout.
     */
    private void addRoomDescriptionAndImages(final BufferedImage[] theDiscRoomImages,
                                             final GridBagConstraints theConstraints) {
        int colCount = 0;

        JLabel descriptionLabel = createDescriptionLabel(KEY_DESCRIPTIONS[KEY_DESCRIPTIONS.length - 1]);
        theConstraints.gridx = colCount++;
        add(descriptionLabel, theConstraints);

        for (BufferedImage image : theDiscRoomImages) {
            addImageToPanel(image, theConstraints, colCount++);
            if (colCount >= COLUMNS_PER_ROW) {
                colCount = 0;
                theConstraints.gridy++;
            }
        }
    }

    /**
     * Adds a single description and its corresponding image to the panel.
     *
     * @param theDescription the description text.
     * @param theImage the image to display.
     * @param theConstraints the grid bag constraints used for layout.
     * @param theColumnCount the current column count for positioning.
     */
    private void addDescriptionWithImage(final String theDescription, final BufferedImage theImage,
                                         final GridBagConstraints theConstraints, int theColumnCount) {
        JLabel descriptionLabel = createDescriptionLabel(theDescription);
        theConstraints.gridx = theColumnCount;
        add(descriptionLabel, theConstraints);

        addImageToPanel(theImage, theConstraints, theColumnCount + 1);
    }

    /**
     * Creates a JLabel for a description.
     *
     * @param theDescription the description text.
     * @return a JLabel configured with the specified description.
     */
    private JLabel createDescriptionLabel(final String theDescription) {
        JLabel label = new JLabel(theDescription);
        label.setBackground(Color.BLACK);
        label.setForeground(Color.WHITE);
        label.setFont(Fonts.getPixelFont(14));
        return label;
    }

    /**
     * Adds an image to the panel at the specified position.
     *
     * @param theImage the image to add.
     * @param theConstraints the grid bag constraints used for layout.
     * @param theColumn the column position to add the image.
     */
    private void addImageToPanel(final BufferedImage theImage,
                                 final GridBagConstraints theConstraints,
                                 final int theColumn) {
        JLabel imageLabel = new JLabel(new ImageIcon(theImage.getScaledInstance(
                                       ICON_SIZE, ICON_SIZE, Image.SCALE_DEFAULT)));
        theConstraints.gridx = theColumn;
        add(imageLabel, theConstraints);
    }
}