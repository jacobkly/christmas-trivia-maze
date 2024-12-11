package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class contains all objects to be serialized.
 *
 * @author Cai Spidel
 * @version 1.0
 */
public final class SerialWrapper implements Serializable {

    /** The serialVersionUID for this object. */
    @Serial
    private static final long serialVersionUID = 1L;

    /** The player to be saved. */
    private final Player myPlayer;

    /** The maze to be saved. */
    private final Maze myMaze;

    /**
     * The components to be saved.
     *
     * @param thePlayer the player to be saved
     * @param theMaze the maze to be saved
     */
    public SerialWrapper(final Player thePlayer, final Maze theMaze) {
        myPlayer = thePlayer;
        myMaze = theMaze;
    }

    /**
     * Get the player that was saved
     *
     * @return the player that was saved
     */
    public Player getPlayer() {
        return myPlayer;
    }

    /**
     * Gets the maze that was saved
     *
     * @return the maze that was saved
     */
    public Maze getMaze() {
        return myMaze;
    }
}
