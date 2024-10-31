package model;

import javax.swing.*;
import java.awt.*;

/**
 * Models the rooms of the maze.
 */
public class Room {
    /** The letters corrosponding to an array. */
    private static final char[] NESW_NUMS = new char[]{'n', 'e', 's', 'w'};

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
    private boolean myHasChangedFromLastComp;

    /**
     * Creates a Room object with default values
     */
    public Room() {
        myRoomImageMerger = new RoomImageMerger();

        myNESWDoors = new boolean[]{false, false, false, false};
        myQuestion = new Question();
        myItem = new Item(0);
        myHasChangedFromLastComp = true;

        myNESWRoom = new Image[5];
        for(int i = 0; i < 4; i++) {
            setDoor(i, false);
        }
        updateRoomImages();
    }


    /**
     * sets the images used for the room.
     *
     */
    private void updateRoomImages() {
        if(this.isVisible()) {
            myNESWRoom[4] = new ImageIcon("./imageFiles/roomFiles/fillRoom/lndscFillRoom.png").getImage();
        } else {
            myNESWRoom[4] = new ImageIcon("./imageFiles/roomFiles/fillRoom/lockFillRoom.png").getImage();
        }
    }


    /**
     * Sets the door according to the values.
     * throws an exception if theNESW is not 0, 1, 2, or 3.
     *
     * @param theNESW the direction, represented by an int.
     * @param theDoor the state of the door.
     */
    public void setDoor(int theNESW, boolean theDoor) {
        if(theNESW > 3 || theNESW < 0) {
            throw new IllegalArgumentException("NESW must be between 0 and 3");
        }
        if(myNESWDoors[theNESW] != theDoor) {
            String result;
            myNESWDoors[theNESW] = theDoor;
            if (theDoor) {
                result = "./imageFiles/roomFiles/wiDoor/"
                        + NESW_NUMS[theNESW] +"WiDoor.png";
                myNESWRoom[theNESW] = new ImageIcon("./imageFiles/roomFiles/wiDoor/"
                        + NESW_NUMS[theNESW] +"WiDoor.png").getImage();
            } else {
                result = "./imageFiles/roomFiles/noDoor/"
                        + NESW_NUMS[theNESW] + "NoDoor.png";
            }
            myNESWRoom[theNESW] = new ImageIcon(result).getImage();
        }
        myHasChangedFromLastComp = true;
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
     * Returns whether this room has an eastern door.
     *
     * @return whether this has an east door.
     */
    public boolean getHasDoorEast() { return myNESWDoors[1]; }

    /**
     * Returns whether this room has a southern door.
     *
     * @return whether this has a south door.
     */
    public boolean getHasDoorSouth() { return myNESWDoors[2]; }

    // get this of this, maze should query room which queries image
    /**
     * Returns the question associated with this room.
     *
     * @return the question.
     */
    public Question getQuestion() { return myQuestion; }

    /**
     * Gets whether this room is visible.
     * This room is visible if it's question has been answered.
     *
     * @return whether this room is visible.
     */
    public boolean isVisible() { return myQuestion.isAnswered(); }

    /**
     * Returns the image that represent the room.
     *
     * @return the merged image.
     */
    public Image getRoomImage() {
        if(myHasChangedFromLastComp) {
            updateRoomImages();
            myHasChangedFromLastComp = false;
        }
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
    public boolean tryAnswer(String thePossibleAnswer) { return myQuestion.checkAnswer(thePossibleAnswer); }


}
