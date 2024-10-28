package model;

public class Player {

    private final static int HINT_COUNT = 3;

    private final String myName;

    private final int myMaxHealth;

    private int myHealthCount;

    private int myHintsCollected;

    private int myHintsUsed;

    private int myRoomsAnswered;

    public Player(final String theName, final int theMaxHealth) {
        myName = theName;
        myMaxHealth = theMaxHealth;
    }

    public String getName() {
        return myName;
    }

    public int getInitialHealth() {
        return myMaxHealth;
    }

    public int getHealthCount() {
        return myHealthCount;
    }

    public void setHealthCount(final int theHealthCount) {
        if (theHealthCount < 0 && theHealthCount > myMaxHealth) {
            throw new IllegalArgumentException("Player health must be between 0 and " + myMaxHealth);
        } else {
            myHealthCount = theHealthCount;
        }
    }

    public int getHintsCollected() {
        return myHintsCollected;
    }

    public void setHintsCollected(final int theHintsCollected) {
        if (theHintsCollected < 0) {
            throw new IllegalArgumentException("Player cannot collect negative hints");
        } else {
            myHintsCollected = theHintsCollected;
        }
    }

    public int getHintsUsed() {
        return myHintsUsed;
    }

    public void setHintsUsed(final int theHintsUsed) {
        myHintsUsed = theHintsUsed;
    }

    public int getRoomsAnswered() {
        return myRoomsAnswered;
    }

    public void setRoomsAnswered(final int theRoomsAnswered) {
        myRoomsAnswered = theRoomsAnswered;
    }

    /**
     * Returns a string representation of the Player object, containing the current information such
     * as name, health, hint counts and other relevant attributes.
     *
     * @return A string containing the player's current information.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Player name : ").append(myName).append("\n");
        sb.append("Health: ").append(myHealthCount).append(" out of ").append(myMaxHealth).append("\n");
        sb.append("Hints used: ").append(myHintsUsed).append(" out of ").append(myHintsCollected)
                .append(" collected").append("\n");
        sb.append("Rooms answered: ").append(myRoomsAnswered).append("\n");

        return sb.toString();
    }
}
