package model;

/**
 * Models the rooms of the maze.
 */
public class Room {
    /** boolean of if the room has a east passage. */
    final private boolean myDoorEast;
    /** boolean of if the room has a south passage. */
    final private boolean myDoorSouth;

    final private Question myQuestion;
    private Item myItem;

    /**
     * Creates a Room object with default values
     */
    public Room() {
        myDoorEast = true;
        myDoorSouth = true;
        myQuestion = new Question();
        myItem = new Item(0);
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
     * Returns whether this room has an eastern door.
     *
     * @return whether this has an east door.
     */
    public boolean getHasDoorEast() {
        return myDoorEast;
    }

    /**
     * Returns whether this room has a southern door.
     *
     * @return whether this has a south door.
     */
    public boolean getHasDoorSouth() {
        return myDoorSouth;
    }

    /**
     * Returns the question associated with this room.
     *
     * @return the question.
     */
    public Question getQuestion() {
        return myQuestion;
    }

    /**
     * Gets whether this room is visible.
     * This room is visible if it's question has been answered.
     *
     * @return whether this room is visible.
     */
    public boolean isVisible() {
        return myQuestion.isAnswered();
    }

}
