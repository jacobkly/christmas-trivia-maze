package model;

import java.sql.Struct;

/**
 * Models an Item in the maze.
 */
public class Item {
    /** The type of item, either 0, 1, or 2. */
    final private int myItemType;

    /**
     * Creates an Item
     * ItemType 0 = no item
     * ItemType 1 = hint
     * ItemType 2 = exit
     *
     * @param theItemType the type of the item, represented as an int.
     */
    public Item(int theItemType) {
        if (theItemType > 2 || theItemType < 0) {
            throw new IllegalArgumentException("Item type must be 2, 1, or 0");
        } else {
            myItemType = theItemType;
        }
    }

    /**
     * Returns the type of item this object represents.
     *
     * @return the type of item
     */
    public int getItemType() {
        return myItemType;
    }

    @Override
    public String toString() {
        if (myItemType == 0) {
            return "There is no item";
        } else if (myItemType == 1) {
            return "This item is a hint.";
        } else {
            return "This item is the exit.";
        }
    }
}
