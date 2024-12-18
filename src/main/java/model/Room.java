package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import static model.RoomEnums.*;
import static model.RoomEnums.RoomArrayValues.ROOM_FILL;
import static model.RoomEnums.RoomArrayValues.ROOM_HIGHLIGHT;

/**
 * Class that models functionality of the rooms of a maze.
 *
 * @author Mathew Miller
 * @author Cai Spidel
 * @version 1.0
 */
public final class Room implements Serializable {

    /** The serialVersionUID for this object. */
    @Serial
    private static final long serialVersionUID = 3L;

    /** The question that locks this room. */
    private final Question myQuestion;

    /** The image representation of this room, stored in render order and with the file locations. */
    private final RoomInfo[] myNESWRoom;

    /** a int that tracks the correct fill of this room.  */
    private final int myFillNum;

    /** Tracks the visibility status of this room, 0 is fully visible, 1 is partially visible, 2 is not visible. */
    private Visibility myVisibility;

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
        myFillNum = random.nextInt(ROOM_INFOS.length - ROOM_INFO_FILL_START) + ROOM_INFO_FILL_START;

        myNESWRoom = new RoomInfo[ROOM_ARRAY_VALUES.length];
        setHigLig(false);

        for(int i = 0; i < 4; i++) {
            setDoor(DOOR_DIRECTIONS[i], true);
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
            myNESWRoom[ROOM_HIGHLIGHT.ordinal()] = RoomInfo.WITH_HIGHLIGHT;
        } else {
            myNESWRoom[ROOM_HIGHLIGHT.ordinal()] = RoomInfo.NO_HIGHLIGHT;
        }
    }


    /**
     * sets the visual room info.
     */
    private void updateRoomInfo() {
        if(myVisibility == Visibility.VISIBLE) {
            myNESWRoom[ROOM_FILL.ordinal()] = ROOM_INFOS[myFillNum];
        } else if(myVisibility == Visibility.LOCKED && myNESWRoom[ROOM_FILL.ordinal()] != RoomInfo.PERM_LOCKED) {
            myNESWRoom[ROOM_FILL.ordinal()] = RoomInfo.LOCKED;
        } else if (myVisibility == Visibility.MYSTERY) {
            myNESWRoom[ROOM_FILL.ordinal()] = RoomInfo.MYSTERY;
        }
    }

    /**
     * Sets the door according to the values.
     * throws an exception if theNESW is not 0, 1, 2, or 3.
     *
     * @param theNESW the direction, represented by an int.
     * @param theDoor the state of the door.
     */
    public void setDoor(final DoorDirection theNESW, final boolean theDoor) {
        myNESWRoom[RoomEnums.doorDirToArrayVal(theNESW)] = RoomEnums.doorDirToRoomInfo(theNESW, theDoor);
    }


    /**
     * returns whether there is a door at a given direction.
     *
     * @return the value of if there is a door.
     */
    public boolean getHasNESWDoor(DoorDirection theNESW) {
        RoomInfo result = RoomEnums.doorDirToRoomInfo(theNESW, true);

        return myNESWRoom[RoomEnums.doorDirToArrayVal(theNESW)] == result;
    }

    /**
     * Gets whether this room is visible.
     *
     * @return whether this room is visible.
     */
    public boolean isVisible() {
        return myVisibility == Visibility.VISIBLE;
    }

    /**
     * Returns whether it is possible to answer the question to this room.
     * It is answerable if the room status is "locked",
     * AND it is NOT permanently locked.
     *
     * @return whether you can answer the question.
     */
    public boolean isAnswerable() {
        return myVisibility == Visibility.LOCKED && !(myNESWRoom[ROOM_FILL.ordinal()] == RoomInfo.PERM_LOCKED);
    }

    /**
     * Returns the room information through an array of their individual components
     *
     * @return the room information through the enums RoomInfo.
     */
    public RoomInfo[] getRoomInfo() {
        updateRoomInfo();
        RoomInfo[] result = myNESWRoom.clone();
        if(result[ROOM_FILL.ordinal()] == RoomInfo.LOCKED && this.isEndpoint()) {
            result[ROOM_FILL.ordinal()] = RoomInfo.ENDPOINT_LOCKED;
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
     * Returns if this is an endpoint or not.
     *
     * @return whether this room is an endpoint.
     */
    public boolean isEndpoint() {
        return myIsEndpoint;
    }

    /**
     * Returns the question of this room.
     *
     * @return the question contained in the room.
     */
    public Question getQuestion() {
        return myQuestion;
    }

    /**
     * Checks if the current visibility is set to MYSTERY.
     *
     * @return true if the visibility is MYSTERY, false otherwise
     */
    public boolean isMystery() {
        return myVisibility == Visibility.MYSTERY;
    }
}
