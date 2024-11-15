package model;

public class Player {

    private final static int MAX_HINT_COUNT = 3;

    private final int myMaxHealth;

    private String myName = "Joe";

    private int myHealthCount;

    /* delete after finished implementing hint/health processing */
    private int myHintsUsed;
    private int myRoomsDiscovered;
    /* --------------------------------------------------------- */

    public Player(final String theName, final int theMaxHealth) {
        myName = theName;
        myMaxHealth = theMaxHealth;
        myHealthCount = theMaxHealth;
        /* delete after finished implementing hint/health processing (default values for now) */
        myHintsUsed = 0;
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

    /* delete after finished implementing hint/health processing */
    public int getHintsUsed() { return myHintsUsed; }
    public int getRoomsDiscovered() { return myRoomsDiscovered; }
    /* --------------------------------------------------------- */
}
