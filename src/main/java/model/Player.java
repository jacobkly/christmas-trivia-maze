package model;

import java.io.Serial;
import java.io.Serializable;

public class Player implements Serializable {
    /** The serialVersionUID for this object. */
    @Serial
    private static final long serialVersionUID = 1L;

    private final static int MAX_HINT_COUNT = 3;

    private final int myMaxHealth;
    private final int myMaxHints;

    private String myName = "Joe";

    private int myHealthCount;

    /* delete after finished implementing hint/health processing */
    private int myHints;
    private int myRoomsDiscovered;
    /* --------------------------------------------------------- */

    public Player(final String theName, final int theMaxHealth, final int theMaxHints) {
        myName = theName;
        myMaxHealth = theMaxHealth;
        myHealthCount = theMaxHealth;
        myMaxHints = theMaxHints;
        /* delete after finished implementing hint/health processing (default values for now) */
        myHints = theMaxHints;
        myRoomsDiscovered = 0;
        /* ---------------------------------------------------------------------------------- */
    }

    public int getMaxHintCount() { return MAX_HINT_COUNT; }

    public String getName() { return myName; }

    public int getMaxHealthCount() { return myMaxHealth; }

    public int getHealthCount() { return myHealthCount; }

    // potentially change to "decrementHealthCount()" instead?
    public void setHealthCount(final int theHealthCount) {
        if (theHealthCount < 0 || theHealthCount > myMaxHealth) {
            throw new IllegalArgumentException("Player health must be between 0 and " + myMaxHealth);
        } else {
            myHealthCount = theHealthCount;
        }
    }

    public int getHints() {
        return myHints;
    }

    public void setHints(final int theHints) {
        myHints = theHints;
    }

    /* delete after finished implementing hint/health processing */
    public int getHintsUsed() { return myMaxHints - myHints; }
    public int getRoomsDiscovered() { return myRoomsDiscovered; }
    /* --------------------------------------------------------- */
}
