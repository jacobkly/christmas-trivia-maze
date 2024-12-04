package tests;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static model.RoomEnums.ROOM_ARRAY_VALUES;
import static org.junit.jupiter.api.Assertions.*;
import model.RoomEnums.*;

/**
 * Tests the model class through black box testing.
 */
public final class MazeBlackBoxTest {

    /** The questions to be tested. */
    private Question[] myQuestions;
    /** The rooms to be tested. */
    private Room[] myRooms;
    /** The maze to be tested. */
    private Maze myMaze;
    /** The player to be tested. */
    private Player myPlayer;

    /**
     * To be run before each test to set up the objects.
     */
    @BeforeEach
    public void setUp() {
        // Question setup
        myQuestions = new Question[4];
        // the boolean question
        myQuestions[0] = new BooleanQuestion("Is this true?", true);

        // the multiple choice question
        List<String> s = new ArrayList<>();
        s.add("A");
        s.add("C");
        s.add("D");
        myQuestions[1] = new MultipleChoiceQuestion("Which is B?", "B", s);

        // the text input question
        myQuestions[2] = new TextInputQuestion("How true is this?", "Very");

        // an extra boolean question
        myQuestions[3] = new BooleanQuestion("This might be false", false);

        // the room setup
        myRooms = new Room[4];
        myRooms[0] = new Room(myQuestions[0]);
        myRooms[1] = new Room(myQuestions[1]);
        myRooms[2] = new Room(myQuestions[2]);
        myRooms[3] = new Room(myQuestions[3]);

        // the maze setup
        List<Question> q = new ArrayList<>();
        q.add(myQuestions[0]);
        q.add(myQuestions[1]);
        q.add(myQuestions[2]);
        q.add(myQuestions[3]);
        myMaze = new Maze(q, 2, 2);

        // player setup
        myPlayer = new Player("name", 3, 2);
    }


    /**
     * Tests the updateRoomVisibility method.
     */
    @Test
    public void testUpdateVisibility() {
        myMaze.updateRoomVisibility();
        // find the starting point
        int row = -1;
        int col = -1;
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                if(myMaze.getRoom(i, j).isVisible()) {
                    row = i;
                    col = j;
                }
            }
        }
        assertTrue(myMaze.getRoom(row, col).isVisible());
        // check that the rooms directly above and to the side are LOCKED
        int row2 = 0;
        int col2 = 0;
        if(row == 0) {
            row2 = 1;
        }
        if(col == 0) {
            col2 = 1;
        }
        assertTrue(myMaze.getRoom(row2, col).isAnswerable());
        assertTrue(myMaze.getRoom(row, col2).isAnswerable());

        // check that the final square is MYSTERY
        assertFalse(myMaze.getRoom(row2, col2).isVisible());
        assertFalse(myMaze.getRoom(row2, col2).isAnswerable());

        // makes sure there is no infinite recursion.
        myMaze.getRoom(row2, col).setVisibility(Visibility.VISIBLE);
        myMaze.updateRoomVisibility();

        assertTrue(myMaze.getRoom(row2, col).isVisible());
        assertTrue(myMaze.getRoom(row, col2).isAnswerable());
        assertTrue(myMaze.getRoom(row2, col2).isAnswerable());
    }

    /**
     * Tests the getRows and getCols methods.
     */
    @Test
    public void testGetRowsGetCols() {
        assertEquals(2, myMaze.getRows());
        assertEquals(2, myMaze.getCols());

        assertNotEquals(1, myMaze.getRows());
        assertNotEquals(3, myMaze.getCols());
    }

    /**
     * Tests the setRoomHigLig functionality.
     * Also tests the getCurrentlySelectedRoom functionality.
     */
    @Test
    public void testRoomSelectionCapabilities() {
        myMaze.setSelectedRoom(myMaze.getRoom(0, 0));
        assertEquals(myMaze.getCurrentlySelectedRoom().getQuestion(), myRooms[0].getQuestion());
        myMaze.setSelectedRoom(myMaze.getRoom(0, 1));
        assertNotEquals(myMaze.getCurrentlySelectedRoom().getQuestion(), myRooms[0].getQuestion());
        assertEquals(myMaze.getCurrentlySelectedRoom().getQuestion(), myRooms[1].getQuestion());
    }

    /**
     * Tests the getRoom method of the maze.
     */
    @Test
    public void testGetRoom() {
        // uses the question located in the room to test equivalence
        assertEquals(myMaze.getRoom(0, 0).getQuestion(), myQuestions[0]);
        assertNotEquals(myMaze.getRoom(0, 0).getQuestion(), myQuestions[1]);

    }

    /**
     * Tests that the maze constructor works correctly.
     */
    @Test
    public void testMazeConstructor() {
        // test that each room exists through the contained questions
        assertEquals(myMaze.getRoom(0, 0).getQuestion(), myQuestions[0]);
        assertEquals(myMaze.getRoom(0, 1).getQuestion(), myQuestions[1]);
        assertEquals(myMaze.getRoom(1, 0).getQuestion(), myQuestions[2]);
        assertEquals(myMaze.getRoom(1, 1).getQuestion(), myQuestions[3]);

        // test that a starting position exists (there is a landscape somewhere)
        // verify there is only one
        boolean isStart = false;
        boolean isMoreThanOneStart = false;
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                if(myMaze.getRoom(i, j).isVisible()) {
                    if(isStart) {
                        isMoreThanOneStart = true;
                    }
                    isStart = true;
                }
            }
        }
        assertTrue(isStart);
        assertFalse(isMoreThanOneStart);

        // test that an ending position exists (iterate through the rooms)
        boolean isEndpoint = false;
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                if(myMaze.getRoom(i, j).isEndpoint()) {
                    isEndpoint = true;
                }
            }
        }
        assertTrue(isEndpoint);

        // test that borders of rooms are made properly
        RoomInfo[] topLeft = makeDefaultRoomInfo();
        topLeft[RoomEnums.doorDirToArrayVal(DoorDirection.NORTH)] = RoomInfo.NORTH_CLOSED;
        topLeft[RoomEnums.doorDirToArrayVal(DoorDirection.WEST)] = RoomInfo.WEST_CLOSED;
        compareRoomInfoMinusFill(myMaze.getRoom(0, 0).getRoomInfo(), topLeft);

        RoomInfo[] topRight = makeDefaultRoomInfo();
        topRight[RoomEnums.doorDirToArrayVal(DoorDirection.NORTH)] = RoomInfo.NORTH_CLOSED;
        topRight[RoomEnums.doorDirToArrayVal(DoorDirection.EAST)] = RoomInfo.EAST_CLOSED;
        compareRoomInfoMinusFill(myMaze.getRoom(0, 1).getRoomInfo(), topRight);

        RoomInfo[] bottomLeft = makeDefaultRoomInfo();
        bottomLeft[RoomEnums.doorDirToArrayVal(DoorDirection.SOUTH)] = RoomInfo.SOUTH_CLOSED;
        bottomLeft[RoomEnums.doorDirToArrayVal(DoorDirection.WEST)] = RoomInfo.WEST_CLOSED;
        compareRoomInfoMinusFill(myMaze.getRoom(1, 0).getRoomInfo(), bottomLeft);

        RoomInfo[] bottomRight = makeDefaultRoomInfo();
        bottomRight[RoomEnums.doorDirToArrayVal(DoorDirection.SOUTH)] = RoomInfo.SOUTH_CLOSED;
        bottomRight[RoomEnums.doorDirToArrayVal(DoorDirection.EAST)] = RoomInfo.EAST_CLOSED;
        compareRoomInfoMinusFill(myMaze.getRoom(1, 1).getRoomInfo(), bottomRight);

    }


    /**
     * Makes a default array of room information.
     *
     * @return a default room information array.
     */
    private RoomInfo[] makeDefaultRoomInfo() {
        RoomInfo[] result = new RoomInfo[ROOM_ARRAY_VALUES.length];
        result[RoomArrayValues.NORTH_DOOR.ordinal()] = RoomInfo.NORTH_OPEN;
        result[RoomArrayValues.EAST_DOOR.ordinal()] = RoomInfo.EAST_OPEN;
        result[RoomArrayValues.SOUTH_DOOR.ordinal()] = RoomInfo.SOUTH_OPEN;
        result[RoomArrayValues.WEST_DOOR.ordinal()] = RoomInfo.WEST_OPEN;
        result[RoomArrayValues.ROOM_FILL.ordinal()] = RoomInfo.MYSTERY;
        result[RoomArrayValues.ROOM_HIGHLIGHT.ordinal()] = RoomInfo.NO_HIGHLIGHT;
        return result;
    }


    /**
     * Compares the room info of two seperate room info arrays.
     * Does not compare the fill values.
     *
     * @param theInfo the room being tested.
     * @param theTestInfo the test values to be used.
     */
    private void compareRoomInfoMinusFill(RoomInfo[] theInfo, RoomInfo[] theTestInfo) {
        assertEquals(theInfo[RoomArrayValues.NORTH_DOOR.ordinal()], theTestInfo[RoomArrayValues.NORTH_DOOR.ordinal()]);
        assertEquals(theInfo[RoomArrayValues.EAST_DOOR.ordinal()], theTestInfo[RoomArrayValues.EAST_DOOR.ordinal()]);
        assertEquals(theInfo[RoomArrayValues.SOUTH_DOOR.ordinal()], theTestInfo[RoomArrayValues.SOUTH_DOOR.ordinal()]);
        assertEquals(theInfo[RoomArrayValues.WEST_DOOR.ordinal()], theTestInfo[RoomArrayValues.WEST_DOOR.ordinal()]);
        assertEquals(theInfo[RoomArrayValues.ROOM_HIGHLIGHT.ordinal()],
                theTestInfo[RoomArrayValues.ROOM_HIGHLIGHT.ordinal()]);
    }

    /**
     * Tests the SerialWrapper class
     */
    @Test
    public void testSerialWrapper() {
        SerialWrapper wrapper = new SerialWrapper(myPlayer, myMaze);
        assertEquals(myPlayer, wrapper.getPlayer());
        assertEquals(myMaze, wrapper.getMaze());
    }

    // player tests do not yet test anything marked as "maybe change"
    /**
     * Tests the Player class
     */
    @Test
    public void testPlayerConstructor() {
        assertEquals("name", myPlayer.getName());
        assertEquals(3, myPlayer.getMaxHealth());
        assertEquals(3, myPlayer.getHealth());
        assertEquals(2, myPlayer.getHints());

    }

}