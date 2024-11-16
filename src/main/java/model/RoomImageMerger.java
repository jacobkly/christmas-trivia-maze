package model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * A class that merges 128x128 images together
 */
public class RoomImageMerger {

    /**
     * Creates the RoomImageMerger.
     */

    private RoomImageMerger() {

    }



    /**
     * Merges a 128x128 set of images together.
     * The images are obtained from the string representations of file locations.
     * This is the visual representation of a room.
     *
     * @param theImageFiles the images to be merged, in render order.
     * @return the merged image.
     */
    public static Image MergeImage(final String[] theImageFiles) {
        int width = 128;
        int height = 128;

        Image mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = mergedImage.getGraphics();

        for (String theImageFile : theImageFiles) {
            graphics.drawImage(new ImageIcon(Objects.requireNonNull
                    (RoomImageMerger.class.getResource(theImageFile))).getImage(), 0, 0, null);
        }
        graphics.dispose();

        return mergedImage;
    }

}
