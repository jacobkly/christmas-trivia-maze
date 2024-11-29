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
     * Tests the Player constructor for correct initialization.
     */
    @Test
    public void testPlayerInitialization() {
        Player player = new Player("Bob", 6, 3);

        assertEquals("Bob", player.getName(),
                "Player name should match the input.");
        assertEquals(6, player.getMaxHealth(),
                "Maximum health should match the input.");
        assertEquals(6, player.getHealth(),
                "Current health should initially equal maximum health.");
        assertEquals(3, player.getMaxHints(),
                "Maximum hints should match the input.");
        assertEquals(3, player.getHints(),
                "Hints should initially equal maximum hints.");
        assertEquals(0, player.getRoomsDiscovered(),
                "Rooms discovered should initially be zero.");
    }

    /**
     * Tests the getMaxHints method.
     */
    @Test
    public void testGetMaxHints() {
        Player player = new Player("Bob", 8, 5);
        assertEquals(5, player.getMaxHints(), "Maximum hints should be 5.");
    }

    /**
     * Tests the getHints method.
     */
    @Test
    public void testGetHints() {
        Player player = new Player("Bob", 6, 4);
        assertEquals(4, player.getHints(),
                "Hints should match the initialized value.");
    }

    /**
     * Tests the setHints method.
     */
    @Test
    public void testSetHints() {
        Player player = new Player("Bob", 6, 4);
        player.setHints(2);
        assertEquals(2, player.getHints(), "Hints should be updated to 2.");
    }

    /**
     * Tests the getHintsUsed method.
     */
    @Test
    public void testGetHintsUsed() {
        Player player = new Player("Bob", 8, 4);
        player.setHints(1);
        assertEquals(3, player.getHintsUsed(), "Hints used should be 3.");
    }

    /**
     * Tests the getMaxHealth method.
     */
    @Test
    public void testGetMaxHealth() {
        Player player = new Player("Bob", 7, 4);
        assertEquals(7, player.getMaxHealth(),
                "Maximum health should match the input.");
    }

    /**
     * Tests the getHealth and setHealth methods for valid health values.
     */
    @Test
    public void testSetAndGetHealthValid() {
        Player player = new Player("Bob", 6, 3);
        player.setHealth(4);
        assertEquals(4, player.getHealth(), "Health should be updated to 4.");
    }

    /**
     * Tests the setHealth method for invalid health values.
     */
    @Test
    public void testSetHealthCountInvalid() {
        Player player = new Player("Bob", 6, 3);
        assertThrows(IllegalArgumentException.class, () -> player.setHealth(-1),
                "Health below 0 should throw an exception.");
        assertThrows(IllegalArgumentException.class, () -> player.setHealth(7),
                "Health above maximum should throw an exception.");
    }

    /**
     * Tests the getRoomsDiscovered method.
     */
    @Test
    public void testGetRoomsDiscovered() {
        Player player = new Player("Bob", 6, 3);
        assertEquals(0, player.getRoomsDiscovered(),
                "Rooms discovered should initially be 0.");
    }

    /**
     * Tests the setRoomsDiscovered method.
     */
    @Test
    public void testSetRoomsDiscovered() {
        Player player = new Player("Bob", 6, 3);
        player.setRoomsDiscovered(5);
        assertEquals(5, player.getRoomsDiscovered(),
                "Rooms discovered should be updated to 5.");
    }

    /**
     * Tests the getPlayerStatistics method.
     */
    @Test
    public void testGetPlayerStatistics() {
        Player player = new Player("Bob", 10, 4);
        player.setHealth(8);
        player.setHints(2);
        player.setRoomsDiscovered(3);

        String[] expectedStats = {
                "Player name: Bob",
                "Health left: 8 out of 10",
                "Hints used: 2 out of 4",
                "Rooms discovered: 3"
        };

        assertArrayEquals(expectedStats, player.getPlayerStatistics(),
                "Player statistics should match expected values.");
    }
}