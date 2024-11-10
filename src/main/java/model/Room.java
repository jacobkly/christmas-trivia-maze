package model;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Room
 *  isSelected
 *  isLocked
 *  isAnswered
 *  isExit
 *  getQuestion
 *
 * RoomButton
 *   setRoom
 */



/**
 * Models the rooms of the maze.
 */
public class Room {
    /** The letters corrosponding to an array. */
    private static final char[] NESW_NUMS = new char[]{'n', 'e', 's', 'w'};

    /** The strings corrosponding to possible room fills. */
    private static final String[] FILL_Strings = new String[]{"lndsc", "santa", "tree"};

    /** The images used for highlighting. */
    private final Image[] myHigLig;

    /** The boolean array for the precense(true)/abcense(false) of doors. */
    private final boolean[] myNESWDoors;

    /** The question that locks this room. */
    private Question myQuestion;

    /** The image representation of this room, stored in render order. */
    private final Image[] myNESWRoom;

    /** The ImageMerger that will help create the room image. */
    private final RoomImageMerger myRoomImageMerger;

    /** a int that tracks the correct fill of this room.  */
    private final int myFillNum;

    /** Tracks the visibility status of this room, 0 is fully visible, 1 is partially visible, 2 is not visible. */
    private int myVisibility;

    /** Holds whether this room is an endpoint.  */
    private boolean myIsEndpoint = false;
    private boolean myStart = false;


    /**
     * Creates a Room with custom values.
     *
     * @param theQuestion the question to be asked.
     */
    public Room(final Question theQuestion) {
        myVisibility = 2;
        Random random = new Random();
        myFillNum = random.nextInt(3);

        myRoomImageMerger = new RoomImageMerger();
        myNESWRoom = new Image[6];
        myHigLig = new Image[2];
        pullHigLigImages();
        setHigLig(false);

        myQuestion = theQuestion;

        myNESWDoors = new boolean[]{true, true, true, true};
        for(int i = 0; i < 4; i++) {
            setDoor(i, myNESWDoors[i]);
        }

        updateRoomImages();

    }

    /**
     * pulls the possible highlight images from the files and stores them.
     */
    private void pullHigLigImages() {
        myHigLig[0] = new ImageIcon(Objects.requireNonNull(getClass().
                getResource("/roomFiles/roomHigLig/roomNoHigLig.png"))).getImage();
        myHigLig[1] = new ImageIcon(Objects.requireNonNull(getClass().
                getResource("/roomFiles/roomHigLig/roomWiHigLig.png"))).getImage();
    }

    /**
     * Sets the highlight status of this room.
     *
     * @param theHigLigStatus whether the room is highlighted or not.
     */
    public void setHigLig(final boolean theHigLigStatus) {
        if(theHigLigStatus) {
            myNESWRoom[5] = myHigLig[1];
        } else {
            myNESWRoom[5] = myHigLig[0];
        }
    }


    /**
     * sets the images used for the room.
     *
     */
    private void updateRoomImages() {
        if(myVisibility == 0) {
            myNESWRoom[4] = new ImageIcon(Objects.requireNonNull(getClass().
                    getResource("/roomFiles/fillRoom/"
                            + FILL_Strings[myFillNum] + "FillRoom.png"))).getImage();
        } else if(myVisibility == 1) {
            myNESWRoom[4] = new ImageIcon(Objects.requireNonNull(getClass().
                    getResource("/roomFiles/fillRoom/lockFillRoom.png"))).getImage();
        } else {
            myNESWRoom[4] = new ImageIcon(Objects.requireNonNull(getClass().
                    getResource("/roomFiles/fillRoom/mystFillRoom.png"))).getImage();
        }
    }

    /**
     * Sets the door according to the values.
     * throws an exception if theNESW is not 0, 1, 2, or 3.
     *
     * @param theNESW the direction, represented by an int.
     * @param theDoor the state of the door.
     */
    public void setDoor(final int theNESW, final boolean theDoor) {
        if(theNESW > 3 || theNESW < 0) {
            throw new IllegalArgumentException("NESW must be between 0 and 3");
        } else {
            myNESWDoors[theNESW] = theDoor;
            setDoorVisual(theNESW, theDoor);
        }
    }

    /**
     * Sets the visual status of the doors.
     *
     * @param theNESW the direction, represented by an int.
     * @param theDoor the state of the door visual. .
     */
    private void setDoorVisual(final int theNESW, final boolean theDoor) {
        if(theNESW > 3 || theNESW < 0) {
            throw new IllegalArgumentException("NESW must be between 0 and 3");
        }
        String result = "/roomFiles/";
        if (theDoor) {
            result += "wiDoor/" + NESW_NUMS[theNESW] + "WiDoor.png";
        } else {
            result += "noDoor/" + NESW_NUMS[theNESW] + "NoDoor.png";
        }
        myNESWRoom[theNESW] = new ImageIcon(Objects.requireNonNull(getClass().getResource(result))).getImage();
    }

    /**
     * returns whether there is a door at a given direction.
     * uses an int to represent the direction.
     * 0 = north, 1 = east
     * 2 = south, 3 = west
     *
     * @return the value of if there is a door.
     */
    public boolean getHasNESWDoor(int theNESW) {
        return myNESWDoors[theNESW];
    }

    /**
     * Gets whether this room is visible.
     * This room is visible if it's question has been answered.
     *
     * @return whether this room is visible.
     */
    public boolean getIsFullyVisible() {
        return myVisibility == 0;
    }

    public boolean isLocked() {
        return myVisibility == 1;
    }

    public boolean isAnswered() {
        return myVisibility == 0;
    }

    public boolean isAnswerable() {
        return myVisibility == 2 && !myStart;
    }

    /**
     * Returns the image that represent the room.
     *
     * @return the merged image.
     */
    public Image getRoomImage() {
        updateRoomImages();
        return myRoomImageMerger.MergeImage(myNESWRoom);
    }

//    /**
//     * Tries a possible answer to the question
//     * This will also answer the question if it is correct.
//     * If correct, it will return true.
//     *
//     * @param thePossibleAnswer the answer the user is trying.
//     * @return whether the answer is correct or not.
//     */
//    public boolean tryAnswer(final String thePossibleAnswer) {
//        if(myQuestion.checkAnswer(thePossibleAnswer)) {
//            myVisibility = 0;
//        }
//        return getIsFullyVisible();
//    }



    /**
     * Sets this room to be an endpoint.
     */
    public void setAsEndpoint() {
        myIsEndpoint = true;
    }

    public void setAsStart() {
        myStart = true;
    }

    public boolean isStart() {
        return myStart;
    }

    /**
     * Sets the visibility of the room.
     * 0 = fully visible
     * 1 = partially visible
     * 2 = not visible.
     *
     * @param theVisibility the visibility level.
     */
    public void setVisibility(final int theVisibility) {
        if(theVisibility > 2 || theVisibility < 0) {
            throw new IllegalArgumentException("Visibility must be between 0 and 2");
        }
        myVisibility = theVisibility;
    }

    /**
     * Returns if this is an endpoint or not.
     *
     * @return whether this room is an endpoint.
     */
    public boolean isEndpoint() {
        return myIsEndpoint;
    }

    public Question getQuestion() {
        return myQuestion;
    }
}
