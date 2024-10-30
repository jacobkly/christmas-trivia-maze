package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RoomImageMerger {

    public RoomImageMerger() {

    }

    /**
     * Merges a 128x128 set of images together.
     * This is the visual representation of a room.
     *
     * @param theImages the images to be merged, in render order.
     * @return the merged image.
     */
    public Image MergeImage(Image[] theImages) {
        int width = 128;
        int height = 128;

        Image mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = mergedImage.getGraphics();

        for (Image theImage : theImages) {
            graphics.drawImage(theImage, 0, 0, null);
        }
        graphics.dispose();

        return mergedImage;
    }

}
