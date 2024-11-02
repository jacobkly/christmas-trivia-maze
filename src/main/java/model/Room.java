package model;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Random;

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

    /** The boolean array for the precense/abcense of doors. */
    private final boolean[] myNESWDoors;

    /** The question that locks this room. */
    final private Question myQuestion;

    /** The item contained in this room. */
    private Item myItem;

    /** The image representation of this room, stored in render order. */
    private final Image[] myNESWRoom;

    /** The ImageMerger that will help create the room image. */
    private final RoomImageMerger myRoomImageMerger;

    /** Tracks whether the image properties have changed between the last image compilation. */
    // private boolean myHasChangedFromLastComp;

    /** a int that tracks the correct fill of this room.  */
    private final int myFillNum;

    /** Tracks if this room is fully visible. */
    private boolean myIsFullyVisible;

    /**
     * Creates a Room object with default values
     */
    public Room() {
        myIsFullyVisible = false;
        Random random = new Random();
        myFillNum = random.nextInt(3);

        myRoomImageMerger = new RoomImageMerger();
        myNESWRoom = new Image[6];
        myHigLig = new Image[2];
        pullHigLigImages();
        setHigLig(false);

        myNESWDoors = new boolean[]{false, false, false, false};
        myQuestion = new Question();
        myItem = new Item(0);
        // myHasChangedFromLastComp = true;

        for(int i = 0; i < 4; i++) {
            setDoor(i, !myNESWDoors[i]);
        }
        updateRoomImages();
    }

    /**
     * Creates a Room with custom values.
     *
     * @param theQuestion the question to be asked.
     * @param theIsFullyVisible whether this room starts visible, regardless of the question status.
     */
    public Room(final Question theQuestion, final boolean theIsFullyVisible) {
        myIsFullyVisible = theIsFullyVisible;
        Random random = new Random();
        myFillNum = random.nextInt(3);

        myRoomImageMerger = new RoomImageMerger();
        myNESWRoom = new Image[6];
        myHigLig = new Image[2];
        pullHigLigImages();
        setHigLig(false);

        myNESWDoors = new boolean[]{false, false, false, false};
        myQuestion = theQuestion;
        myItem = new Item(0);
        // myHasChangedFromLastComp = true;

        for(int i = 0; i < 4; i++) {
            myNESWDoors[i] = !myNESWDoors[i];
            setDoor(i, !myNESWDoors[i]);
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
        // myHasChangedFromLastComp = true;
    }


    /**
     * sets the images used for the room.
     *
     */
    private void updateRoomImages() {
        // add an ! to test landscape images, remove the ! for correct functionality.
        if(this.getIsVisible()) {
            myNESWRoom[4] = new ImageIcon(Objects.requireNonNull(getClass().
                    getResource("/roomFiles/fillRoom/"
                            + FILL_Strings[myFillNum] + "FillRoom.png"))).getImage();
        } else {
            myNESWRoom[4] = new ImageIcon(Objects.requireNonNull(getClass().
                    getResource("/roomFiles/fillRoom/lockFillRoom.png"))).getImage();
        }
    }

    /**
     * Sets the visual to show no information.
     */
    private void setMystRoom() {
        myNESWRoom[4] = new ImageIcon(Objects.requireNonNull(getClass().
                getResource("/roomFiles/fillRoom/mystFillRoom.png"))).getImage();
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
        }
        if(myNESWDoors[theNESW] != theDoor) {
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
            result += "noDoor/" + NESW_NUMS[theNESW] + "NoDoor.png";
        } else {
            result += "wiDoor/" + NESW_NUMS[theNESW] + "WiDoor.png";
        }
        myNESWRoom[theNESW] = new ImageIcon(Objects.requireNonNull(getClass().getResource(result))).getImage();
        // myHasChangedFromLastComp = true;
    }


    /**
     * Picks up the item, removing it from the room.
     *
     * @return the item in the room.
     */
    public Item pickUpItem(){
        Item result = myItem;
        if(myItem.getItemType() > 0){
            myItem = new Item(0);
        }
        return result;
    }

    /**
     * returns whether there is a door at a given direction.
     * goes in order of North, East, South, West.
     *
     * @return teh values of if there is a door.
     */
    public boolean[] getHasNESWDoors() { return myNESWDoors.clone(); }

    /**
     * Gets whether this room is visible.
     * This room is visible if it's question has been answered.
     *
     * @return whether this room is visible.
     */
    public boolean getIsVisible() { return myIsFullyVisible; }

    /**
     * Returns the image that represent the room.
     *
     * @return the merged image.
     */
    public Image getRoomImage() {
        /*
        if(myHasChangedFromLastComp) {
            updateRoomImages();
            myHasChangedFromLastComp = false;
        }
         */
        updateRoomImages();
        return myRoomImageMerger.MergeImage(myNESWRoom);
    }

    /**
     * Returns the question of this room.
     *
     */
    public String getQuestionText() { return myQuestion.getMyQuestion(); }

    /**
     * Returns the possible answers for the question of this room.
     *
     * @return the possible answers for this question.
     */
    public String[] getPossibleAnswers() { return myQuestion.getMyChoices(); }

    /**
     * Tries a possible answer to the question
     * This will also answer the question if it is correct.
     * If correct, it will return true.
     *
     * @param thePossibleAnswer the answer the user is trying.
     * @return whether the answer is correct or not.
     */
    public boolean tryAnswer(final String thePossibleAnswer) {
        myIsFullyVisible = myQuestion.checkAnswer(thePossibleAnswer);
        return myIsFullyVisible;
    }

    /**
     * Sets the item held in the room to a custom item.
     *
     * @param theItem the item to be held.
     */
    public void setItem(final Item theItem) {
        myItem = theItem;
    }

    public void setIsFullyVisible(final boolean theIsFullyVisible) {
        myIsFullyVisible = theIsFullyVisible;
    }


}
