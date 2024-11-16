package model;

import java.awt.*;
import java.io.Serializable;
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
public class Room implements Serializable {

    /** The serialVersionUID for this object. */
    private static final long serialVersionUID = 1L;

    /** The letters corrosponding to an array. */
    private static final char[] NESW_NUMS = new char[]{'n', 'e', 's', 'w'};

    /** The strings corrosponding to possible room fills. */
    private static final String[] FILL_STRINGS = new String[]{"lndsc", "santa", "tree"};

    /** The boolean array for the precense(true)/abcense(false) of doors. */
    private final boolean[] myNESWDoors;

    /** The question that locks this room. */
    private Question myQuestion;

    /** The image representation of this room, stored in render order and with the file locations. */
    private final String[] myNESWRoom;

    /** a int that tracks the correct fill of this room.  */
    private final int myFillNum;

    /** Tracks the visibility status of this room, 0 is fully visible, 1 is partially visible, 2 is not visible. */
    private Visibility myVisibility;

    public enum Visibility {
        VISIBLE,
        LOCKED,
        MYSTERY;
    }

    /** The individual components that make up the info of a room. */
    public enum RoomInfo {
        NORTH_OPEN,
        NORTH_CLOSED,
        EAST_OPEN,
        EAST_CLOSED,
        SOUTH_OPEN,
        SOUTH_CLOSED,
        WEST_OPEN,
        WEST_CLOSED,

        LNDSC,
        SANTA,
        TREE,

        LOCKED,
        MYSTERY,

        WIHIGLIG,
        NOHIGLIG
    }

    /** Holds whether this room is an endpoint.  */
    private boolean myIsEndpoint = false;

    /**
     * Creates a Room with custom values.
     *
     * @param theQuestion the question to be asked.
     */
    public Room(final Question theQuestion) {
        myQuestion = theQuestion;

        myVisibility = Visibility.MYSTERY;

        Random random = new Random();
        myFillNum = random.nextInt(3);

        myNESWRoom = new String[6];
        setHigLig(false);

        myNESWDoors = new boolean[]{true, true, true, true};
        for(int i = 0; i < 4; i++) {
            setDoor(i, myNESWDoors[i]);
        }

        updateRoomImages();

    }

    /**
     * Sets the highlight status of this room.
     *
     * @param theHigLigStatus whether the room is highlighted or not.
     */
    public void setHigLig(final boolean theHigLigStatus) {
        if(theHigLigStatus) {
            myNESWRoom[5] = "/roomFiles/roomHigLig/roomWiHigLig.png";
        } else {
            myNESWRoom[5] = "/roomFiles/roomHigLig/roomNoHigLig.png";
        }
    }


    /**
     * sets the images used for the room.
     *
     */
    private void updateRoomImages() {
        if(myVisibility == Visibility.VISIBLE) {
            myNESWRoom[4] = "/roomFiles/fillRoom/" + FILL_STRINGS[myFillNum] + "FillRoom.png";
        } else if(myVisibility == Visibility.LOCKED) {
            myNESWRoom[4] = "/roomFiles/fillRoom/lockFillRoom.png";
        } else {
            myNESWRoom[4] = "/roomFiles/fillRoom/mystFillRoom.png";
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
        myNESWRoom[theNESW] = result;
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
    public boolean isVisible() {
        return myVisibility == Visibility.VISIBLE;
    }

    /**
     * Returns whether it is possible to answer the question to this room.
     * It is answerable if the room status is "locked".
     *
     * @return whether you can answer the question.
     */
    public boolean isAnswerable() {
        return myVisibility == Visibility.LOCKED;
    }

    /**
     * Returns the image representation of this room, stored in render order and with the file locations.
     *
     * @return the image representation through file locations.
     */
    public String[] getRoomImage() {
        updateRoomImages();
        return myNESWRoom;
    }

    /**
     * Tries a possible answer to the question
     * This will also answer the question if it is correct.
     * If correct, it will return true.
     *
     * @param thePossibleAnswer the answer the user is trying.
     * @return whether the answer is correct or not.
     */
    public boolean checkAnswer(final String thePossibleAnswer) {
        if(myQuestion.checkAnswer(thePossibleAnswer)) {
            myVisibility = Visibility.VISIBLE;
        }
        return isVisible();
    }

    /**
     * Sets this room to be an endpoint.
     */
    public void setAsEndpoint() {
        myIsEndpoint = true;
    }

    /**
     * Sets the visibility of the room.
     * Uses the enum VISIBILITY
     *
     * @param theVisibility the visibility level.
     */
    public void setVisibility(final Visibility theVisibility) {
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
