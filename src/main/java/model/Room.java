package model;

import java.io.Serializable;
import java.util.*;


/**
 * Models the rooms of the maze.
 */
public class Room implements Serializable {

    /** The serialVersionUID for this object. */
    private static final long serialVersionUID = 1L;

    /** The question that locks this room. */
    private Question myQuestion;

    /** The image representation of this room, stored in render order and with the file locations. */
    private final RoomInfo[] myNESWRoom;

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
        // door values
        NORTH_OPEN, // 0
        NORTH_CLOSED, // 1

        EAST_OPEN, // 2
        EAST_CLOSED, // 3

        SOUTH_OPEN, // 4
        SOUTH_CLOSED, // 5

        WEST_OPEN, // 6
        WEST_CLOSED, // 7

        // status values
        LOCKED, // 8
        MYSTERY, // 9

        // extra status
        PERM_LOCKED, // 10
        ENDPOINT_LOCKED, // 11

        // Highlight values
        WITH_HIGHLIGHT, // 12
        NO_HIGHLIGHT, // 13

        // basic values
        LANDSCAPE, // 14
        SANTA, // 14
        TREE, // 16

        // added values
        FIREPLACE,
        GIFTS,
        MOON

    }
    /** Holds the RoomInfo values for easy access to their integer equivalents. */
    private static final RoomInfo[] myRoomInfoValues = RoomInfo.values();

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
        myFillNum = random.nextInt(6) + 14;

        myNESWRoom = new RoomInfo[6];
        setHigLig(false);

        for(int i = 0; i < 4; i++) {
            setDoor(i, true);
        }

        updateRoomInfo();

    }

    /**
     * Sets the highlight status of this room.
     *
     * @param theHigLigStatus whether the room is highlighted or not.
     */
    public void setHigLig(final boolean theHigLigStatus) {
        if(theHigLigStatus) {
            myNESWRoom[5] = RoomInfo.WITH_HIGHLIGHT;
        } else {
            myNESWRoom[5] = RoomInfo.NO_HIGHLIGHT;
        }
    }


    /**
     * sets the visual room info.
     *
     */
    private void updateRoomInfo() {
        if(myVisibility == Visibility.VISIBLE) {
            myNESWRoom[4] = myRoomInfoValues[myFillNum];
        } else if(myVisibility == Visibility.LOCKED && myNESWRoom[4] != RoomInfo.PERM_LOCKED) {
            myNESWRoom[4] = RoomInfo.LOCKED;
        } else if (myVisibility == Visibility.MYSTERY) {
            myNESWRoom[4] = RoomInfo.MYSTERY;
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
        int result = theNESW * 2;
        if (!theDoor) {
            result ++;
        }
        myNESWRoom[theNESW] = myRoomInfoValues[result];
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
        RoomInfo result = myRoomInfoValues[theNESW * 2];

        return myNESWRoom[theNESW] == result;
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
        return myVisibility == Visibility.LOCKED && !(myNESWRoom[4] == RoomInfo.PERM_LOCKED);
    }

    /**
     * Returns the image representation of this room, stored in render order and with the file locations.
     *
     * @return the image representation through file locations.
     */
    public RoomInfo[] getRoomInfo() {
        updateRoomInfo();
        RoomInfo[] result = myNESWRoom.clone();
        if(result[4] == RoomInfo.LOCKED && this.isEndpoint()) {
            result[4] = RoomInfo.ENDPOINT_LOCKED;
        }
        return result;
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
     * Sets this room to be permanently inaccessable
     */
    public void setInaccessable() {
        for(int i = 0; i < 4; i++) {
            setDoor(i, false);
        }
        myNESWRoom[4] = RoomInfo.PERM_LOCKED;
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
