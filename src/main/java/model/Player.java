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
public final class Player implements Serializable {

    /** The serial version UID for serialization compatibility. */
    @Serial
    private static final long serialVersionUID = 1L;

    /** The player's name. */
    private final String myName;

    /** The maximum health of the player. */
    private final int myMaxHealth;

    /** The maximum number of hints the player can have. */
    private final int myMaxHints;

    /** The player's current health count. */
    private int myHealthCount;

    /** The number of hints remaining for the player. */
    private int myHints;

    /** The number of rooms discovered by the player. */
    private int myRoomsDiscovered;

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
     * Gets the maximum number of hints the player can have.
     *
     * @return the maximum number of hints.
     */
    public int getMaxHints() { return myMaxHints; }

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


    /**
     * Sets the number of rooms the player has discovered.
     *
     * @param theRoomsDiscovered the new number of rooms discovered.
     */
    public void setRoomsDiscovered(final int theRoomsDiscovered) {
        myRoomsDiscovered = theRoomsDiscovered;
    }

    /**
     * Retrieves an array of the player's current statistics, including name, health, hints used,
     * and rooms discovered.
     *
     * @return a String array containing the player's statistics.
     */
    public String[] getPlayerStatistics() {
        String[] playerStats = new String[4];
        playerStats[0] = "Player name: " + getName();
        playerStats[1] = "Health left: " + getHealthCount() + " out of " + getMaxHealthCount();
        playerStats[2] = "Hints used: " + getHintsUsed() + " out of " + getMaxHints();
        playerStats[3] = "Rooms discovered: " + getRoomsDiscovered();
        return playerStats;
    }
}
