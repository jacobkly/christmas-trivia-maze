package view;

import model.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * A class that merges 128x128 images together
 */
public class RoomImageMerger {

    private static final Image[] ROOM_IMAGES = new Image[15];
    static {
        try {
            ROOM_IMAGES[0] = new ImageIcon(Objects.requireNonNull
                    (RoomImageMerger.class.getResource("/roomFiles/wiDoor/nWiDoor.png"))).getImage();
            ROOM_IMAGES[1] = new ImageIcon(Objects.requireNonNull
                    (RoomImageMerger.class.getResource("/roomFiles/noDoor/nNoDoor.png"))).getImage();
            ROOM_IMAGES[2] = new ImageIcon(Objects.requireNonNull
                    (RoomImageMerger.class.getResource("/roomFiles/wiDoor/eWiDoor.png"))).getImage();
            ROOM_IMAGES[3] = new ImageIcon(Objects.requireNonNull
                    (RoomImageMerger.class.getResource("/roomFiles/noDoor/eNoDoor.png"))).getImage();
            ROOM_IMAGES[4] = new ImageIcon(Objects.requireNonNull
                    (RoomImageMerger.class.getResource("/roomFiles/wiDoor/sWiDoor.png"))).getImage();
            ROOM_IMAGES[5] = new ImageIcon(Objects.requireNonNull
                    (RoomImageMerger.class.getResource("/roomFiles/noDoor/sNoDoor.png"))).getImage();
            ROOM_IMAGES[6] = new ImageIcon(Objects.requireNonNull
                    (RoomImageMerger.class.getResource("/roomFiles/wiDoor/wWiDoor.png"))).getImage();
            ROOM_IMAGES[7] = new ImageIcon(Objects.requireNonNull
                    (RoomImageMerger.class.getResource("/roomFiles/noDoor/wNoDoor.png"))).getImage();

            ROOM_IMAGES[8] = new ImageIcon(Objects.requireNonNull
                    (RoomImageMerger.class.getResource("/roomFiles/fillRoom/lockFillRoom.png"))).getImage();
            ROOM_IMAGES[9] = new ImageIcon(Objects.requireNonNull
                    (RoomImageMerger.class.getResource("/roomFiles/fillRoom/mystFillRoom.png"))).getImage();

            ROOM_IMAGES[10] = new ImageIcon(Objects.requireNonNull
                    (RoomImageMerger.class.getResource("/roomFiles/roomHigLig/roomWiHigLig.png"))).getImage();
            ROOM_IMAGES[11] = new ImageIcon(Objects.requireNonNull
                    (RoomImageMerger.class.getResource("/roomFiles/roomHigLig/roomNoHigLig.png"))).getImage();

            ROOM_IMAGES[12] = new ImageIcon(Objects.requireNonNull
                    (RoomImageMerger.class.getResource("/roomFiles/fillRoom/lndscFillRoom.png"))).getImage();
            ROOM_IMAGES[13] = new ImageIcon(Objects.requireNonNull
                    (RoomImageMerger.class.getResource("/roomFiles/fillRoom/santaFillRoom.png"))).getImage();
            ROOM_IMAGES[14] = new ImageIcon(Objects.requireNonNull
                    (RoomImageMerger.class.getResource("/roomFiles/fillRoom/treeFillRoom.png"))).getImage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
     * @param theRoomInfo the images to be merged, in render order.
     * @return the merged image.
     */
    public static Image MergeImage(final Room.RoomInfo[] theRoomInfo) {
        int width = 128;
        int height = 128;

        Image mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = mergedImage.getGraphics();

        for (Room.RoomInfo roomInfo : theRoomInfo) {
            graphics.drawImage(ROOM_IMAGES[roomInfo.ordinal()], 0, 0, null);
        }
        graphics.dispose();

        return mergedImage;
    }

}