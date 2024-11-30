package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import model.Player;

/**
 * Unit tests for the Player class.
 *
 * @author Jacob Klymenko
 * @version 1.0
 */
public class PlayerTest {

    /**
     * Tests the Player constructor for correct initialization of fields.
     */
    @Test
    public void testPlayerInitialization() {
        Player player = new Player("Alice", 6, 3);

        assertEquals("Alice", player.getName(),
                "Player name should match the input.");
        assertEquals(6, player.getMaxHealthCount(),
                "Maximum health should match the input.");
        assertEquals(3, player.getHints(), "Hints should match the input.");
        assertEquals(6, player.getHealthCount(),
                "Current health should initially equal maximum health.");
        assertEquals(0, player.getRoomsDiscovered(),
                "Rooms discovered should initially be zero.");
    }

    /**
     * Tests the getMaxHintCount method.
     */
    @Test
    public void testGetMaxHintCount() {
        Player player = new Player("Alice", 8, 3);
        assertEquals(3, player.getMaxHintCount(),
                "Maximum hint count should be 3.");
    }

    /**
     * Tests the getName method.
     */
    @Test
    public void testGetName() {
        Player player = new Player("Alice", 7, 3);
        assertEquals("Alice", player.getName(),
                "getName should return the correct player name.");
    }

    /**
     * Tests the getMaxHealthCount method.
     */
    @Test
    public void testGetMaxHealthCount() {
        Player player = new Player("Bob", 5, 3);
        assertEquals(5, player.getMaxHealthCount(),
                "getMaxHealthCount should return the correct maximum health.");
    }

    /**
     * Tests setting the health count within valid bounds.
     */
    @Test
    public void testSetHealthCountValid() {
        Player player = new Player("Alice", 8, 3);

        player.setHealthCount(4);
        assertEquals(4, player.getHealthCount(),
                "Health should be updated to 4.");
    }

    /**
     * Tests setting the health count outside valid bounds.
     */
    @Test
    public void testSetHealthCountInvalid() {
        Player player = new Player("Alice", 6, 3);

        assertThrows(IllegalArgumentException.class, () -> player.setHealthCount(-1),
                "Setting health below 0 should throw an exception.");
        assertThrows(IllegalArgumentException.class, () -> player.setHealthCount(7),
                "Setting health above maximum should throw an exception.");
    }

    /**
     * Tests getting and setting the number of hints.
     */
    @Test
    public void testSetAndGetHints() {
        Player player = new Player("Alice", 5, 3);

        player.setHints(2);
        assertEquals(2, player.getHints(), "Hints should be updated to 2.");
    }

    /**
     * Tests the getHintsUsed method.
     */
    @Test
    public void testGetHintsUsed() {
        Player player = new Player("Alice", 8, 3);

        player.setHints(1);
        assertEquals(2, player.getHintsUsed(),
                "Hints used should be calculated correctly.");
    }

    // TODO most likely not needed after implementing functionality elsewhere
    /**
     * Tests the getRoomsDiscovered method.
     */
    @Test
    public void testGetRoomsDiscovered() {
        Player player = new Player("Alice", 4, 3);

        assertEquals(0, player.getRoomsDiscovered(),
                "Rooms discovered should initially be zero.");
    }
}
