package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a player in the game, encapsulating their name, health, hints, and progress.
 * Implements {@link Serializable} for saving player data.
 *
 * @author Jacob Klymenko
 * @author Mathew Miller
 * @author Cai Spidel
 * @version 1.0
 */
public class Player implements Serializable {

    /** The serial version UID for serialization compatibility. */
    @Serial
    private static final long serialVersionUID = 1L;

    /** The maximum number of hints a player can use. */
    private final static int MAX_HINT_COUNT = 3;

    /** The maximum health of the player. */
    private final int myMaxHealth;

    /** The maximum number of hints the player can have. */
    private final int myMaxHints;

    /** The number of rooms discovered by the player. */
    private final int myRoomsDiscovered;

    /** The player's name. */
    private final String myName;

    /** The player's current health count. */
    private int myHealthCount;

    /** The number of hints remaining for the player. */
    private int myHints;

    /**
     * Constructs a new player with the specified name, maximum health, and maximum hints.
     *
     * @param theName the name of the player.
     * @param theMaxHealth the maximum health of the player.
     * @param theMaxHints the maximum number of hints the player can have.
     */
    public Player(final String theName, final int theMaxHealth, final int theMaxHints) {
        myName = theName;
        myMaxHealth = theMaxHealth;
        myHealthCount = theMaxHealth;
        myMaxHints = theMaxHints;
        myHints = theMaxHints;
        myRoomsDiscovered = 0;
    }

    /**
     * Gets the maximum number of hints allowed for a player.
     *
     * @return the maximum hint count.
     */
    public int getMaxHintCount() { return MAX_HINT_COUNT; }

    /**
     * Gets the player's name.
     *
     * @return the name of the player.
     */
    public String getName() { return myName; }

    /**
     * Gets the player's maximum health count.
     *
     * @return the maximum health of the player.
     */
    public int getMaxHealthCount() { return myMaxHealth; }

    /**
     * Gets the player's current health count.
     *
     * @return the current health of the player.
     */
    public int getHealthCount() { return myHealthCount; }

    /**
     * Sets the player's current health count.
     *
     * @param theHealthCount the new health count.
     * @throws IllegalArgumentException if the health count is not between 0 and the player's
     *                                  maximum health.
     */
    public void setHealthCount(final int theHealthCount) {
        if (theHealthCount < 0 || theHealthCount > myMaxHealth) {
            throw new IllegalArgumentException("Player health must be between 0 and " + myMaxHealth);
        } else {
            myHealthCount = theHealthCount;
        }
    }

    /**
     * Gets the number of hints the player currently has.
     *
     * @return the remaining hints of the player.
     */
    public int getHints() {
        return myHints;
    }

    /**
     * Sets the number of hints the player currently has.
     *
     * @param theHints the new hint count.
     */
    public void setHints(final int theHints) {
        myHints = theHints;
    }

    /**
     * Gets the number of hints used by the player.
     *
     * @return the number of hints used.
     */
    public int getHintsUsed() { return myMaxHints - myHints; }

    /**
     * Gets the number of rooms the player has discovered.
     *
     * @return the number of rooms discovered.
     */
    public int getRoomsDiscovered() { return myRoomsDiscovered; }
}
