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
class ModelBlackBoxTest {

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
    void setUp() {
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
        topLeft[DoorDirection.NORTH.ordinal()] = RoomInfo.NORTH_CLOSED;
        topLeft[DoorDirection.WEST.ordinal()] = RoomInfo.WEST_CLOSED;
        compareRoomInfoMinusFill(myMaze.getRoom(0, 0).getRoomInfo(), topLeft);

        RoomInfo[] topRight = makeDefaultRoomInfo();
        topRight[DoorDirection.NORTH.ordinal()] = RoomInfo.NORTH_CLOSED;
        topRight[DoorDirection.EAST.ordinal()] = RoomInfo.EAST_CLOSED;
        compareRoomInfoMinusFill(myMaze.getRoom(0, 1).getRoomInfo(), topRight);

        RoomInfo[] bottomLeft = makeDefaultRoomInfo();
        bottomLeft[DoorDirection.SOUTH.ordinal()] = RoomInfo.SOUTH_CLOSED;
        bottomLeft[DoorDirection.WEST.ordinal()] = RoomInfo.WEST_CLOSED;
        compareRoomInfoMinusFill(myMaze.getRoom(1, 0).getRoomInfo(), bottomLeft);

        RoomInfo[] bottomRight = makeDefaultRoomInfo();
        bottomRight[DoorDirection.SOUTH.ordinal()] = RoomInfo.SOUTH_CLOSED;
        bottomRight[DoorDirection.EAST.ordinal()] = RoomInfo.EAST_CLOSED;
        compareRoomInfoMinusFill(myMaze.getRoom(1, 1).getRoomInfo(), bottomRight);

    }

    /**
     * Tests the door information capabilities of RoomInfo.
     */
    @Test
    public void testRoomDoorInfo() {
        // tests default with doors
        RoomInfo[] testInfo = makeDefaultRoomInfo();
        RoomInfo[] info = myRooms[0].getRoomInfo();
        compareRoomInfo(testInfo, info);

        // removes doors
        myRooms[0].setDoor(DoorDirection.NORTH, false);
        myRooms[0].setDoor(DoorDirection.EAST, false);
        myRooms[0].setDoor(DoorDirection.SOUTH, false);
        myRooms[0].setDoor(DoorDirection.WEST, false);

        // tests that doors are gone
        testInfo[0] = RoomInfo.NORTH_CLOSED;
        testInfo[1] = RoomInfo.EAST_CLOSED;
        testInfo[2] = RoomInfo.SOUTH_CLOSED;
        testInfo[3] = RoomInfo.WEST_CLOSED;
        info = myRooms[0].getRoomInfo();
        compareRoomInfo(testInfo, info);
    }

    /**
     * Tests the fill capabilities of the RoomInfo.
     */
    @Test
    public void testRoomInfoFill() {
        // visibility is default MYSTERY
        RoomInfo[] testInfo = makeDefaultRoomInfo();
        RoomInfo[] info = myRooms[0].getRoomInfo();
        compareRoomInfo(testInfo, info);
        // setting visibility to visible:
        myRooms[0].setVisibility(Visibility.VISIBLE);
        info = myRooms[0].getRoomInfo();
        assertVisible(info[4]);
        // setting visibility to locked:
        myRooms[0].setVisibility(Visibility.LOCKED);
        info = myRooms[0].getRoomInfo();
        assertEquals(RoomInfo.LOCKED, info[4]);
        // setting visibility back to mystery:
        myRooms[0].setVisibility(Visibility.MYSTERY);
        info = myRooms[0].getRoomInfo();
        assertEquals(RoomInfo.MYSTERY, info[4]);
    }

    /**
     * asserts that the RoomInfo is one of the visible types.
     *
     * @param theInfo the RoomInfo.
     */
    private void assertVisible(RoomInfo theInfo) {
        assertNotEquals(theInfo, RoomInfo.NORTH_OPEN);
        assertNotEquals(theInfo, RoomInfo.NORTH_CLOSED);

        assertNotEquals(theInfo, RoomInfo.EAST_OPEN);
        assertNotEquals(theInfo, RoomInfo.EAST_CLOSED);

        assertNotEquals(theInfo, RoomInfo.SOUTH_OPEN);
        assertNotEquals(theInfo, RoomInfo.SOUTH_CLOSED);

        assertNotEquals(theInfo, RoomInfo.WEST_OPEN);
        assertNotEquals(theInfo, RoomInfo.WEST_CLOSED);


        assertNotEquals(theInfo, RoomInfo.MYSTERY);
        assertNotEquals(theInfo, RoomInfo.LOCKED);

        assertNotEquals(theInfo, RoomInfo.NO_HIGHLIGHT);
        assertNotEquals(theInfo, RoomInfo.WITH_HIGHLIGHT);
    }

    /**
     * Tests the roominfo of the highlighting capabilities.
     */
    @Test
    public void testRoomInfoHigLig() {
        RoomInfo[] testInfo = makeDefaultRoomInfo();
        testInfo[5] = RoomInfo.WITH_HIGHLIGHT;
        myRooms[0].setHigLig(true);
        RoomInfo[] info = myRooms[0].getRoomInfo();

        compareRoomInfo(info, testInfo);

        testInfo[5] = RoomInfo.NO_HIGHLIGHT;
        myRooms[0].setHigLig(false);
        info = myRooms[0].getRoomInfo();

        compareRoomInfo(info, testInfo);
    }

    /**
     * Tests the RoomInfo of the room.
     */
    @Test
    public void testRoomInfoDefault() {
        RoomInfo[] testInfo = makeDefaultRoomInfo();
        RoomInfo[] info = myRooms[0].getRoomInfo();
        compareRoomInfo(info, testInfo);
    }

    /**
     * Makes a default array of room information.
     *
     * @return a default room information array.
     */
    private RoomInfo[] makeDefaultRoomInfo() {
        RoomInfo[] result = new RoomInfo[6];
        result[0] = RoomInfo.NORTH_OPEN;
        result[1] = RoomInfo.EAST_OPEN;
        result[2] = RoomInfo.SOUTH_OPEN;
        result[3] = RoomInfo.WEST_OPEN;
        result[4] = RoomInfo.MYSTERY;
        result[5] = RoomInfo.NO_HIGHLIGHT;
        return result;
    }

    /**
     * Compares the room info of two seperate room info arrays.
     *
     * @param theInfo the room being tested.
     * @param theTestInfo the test values to be used.
     */
    private void compareRoomInfo(RoomInfo[] theInfo, RoomInfo[] theTestInfo) {
        compareRoomInfoMinusFill(theInfo, theTestInfo);
        assertEquals(theInfo[RoomArrayValues.ROOM_FILL.ordinal()], theTestInfo[RoomArrayValues.ROOM_FILL.ordinal()]);
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
     * Test the setVisibility capabilities of room.
     */
    @Test
    public void testSetVisibility() {
        // visibility is default MYSTERY, isAnswerable and isVisible should both be false
        assertFalse(myRooms[0].isVisible());
        assertFalse(myRooms[0].isAnswerable());
        // setting visibility to visible:
        myRooms[0].setVisibility(Visibility.VISIBLE);
        assertTrue(myRooms[0].isVisible());
        assertFalse(myRooms[0].isAnswerable());
        // setting visibility to locked:
        myRooms[0].setVisibility(Visibility.LOCKED);
        assertFalse(myRooms[0].isVisible());
        assertTrue(myRooms[0].isAnswerable());
        // setting visibility back to mystery:
        myRooms[0].setVisibility(Visibility.MYSTERY);
        assertFalse(myRooms[0].isVisible());
        assertFalse(myRooms[0].isAnswerable());
    }

    /**
     * Tests the setDoor methods in room.
     */
    @Test
    public void testSetDoor() {
        // tests default with doors
        assertTrue(myRooms[0].getHasNESWDoor(DoorDirection.NORTH));
        assertTrue(myRooms[0].getHasNESWDoor(DoorDirection.EAST));
        assertTrue(myRooms[0].getHasNESWDoor(DoorDirection.SOUTH));
        assertTrue(myRooms[0].getHasNESWDoor(DoorDirection.WEST));

        // removes doors
        myRooms[0].setDoor(DoorDirection.NORTH, false);
        myRooms[0].setDoor(DoorDirection.EAST, false);
        myRooms[0].setDoor(DoorDirection.SOUTH, false);
        myRooms[0].setDoor(DoorDirection.WEST, false);

        // tests that doors are gone
        assertFalse(myRooms[0].getHasNESWDoor(DoorDirection.NORTH));
        assertFalse(myRooms[0].getHasNESWDoor(DoorDirection.EAST));
        assertFalse(myRooms[0].getHasNESWDoor(DoorDirection.SOUTH));
        assertFalse(myRooms[0].getHasNESWDoor(DoorDirection.WEST));
    }

    /**
     * Tests the endpoint setup of a room.
     */
    @Test
    public void testEndpointSetup() {
        assertFalse(myRooms[0].isEndpoint());
        myRooms[0].setAsEndpoint();
        assertTrue(myRooms[0].isEndpoint());
    }

    /**
     * Tests whether the RoomInfo arrays are the correct length.
     */
    @Test
    public void testRoomInfoArrayLength() {
        assertEquals(myRooms[0].getRoomInfo().length, ROOM_ARRAY_VALUES.length);
    }

    /**
     * Tests the accessible aspects of the room constructor.
     */
    @Test
    public void testRoomContructor() {
        // test question
        assertEquals(myRooms[0].getQuestion(), myQuestions[0]);
        assertEquals(myRooms[1].getQuestion(), myQuestions[1]);
        assertEquals(myRooms[2].getQuestion(), myQuestions[2]);
        // visibility is default MYSTERY, isAnswerable and isVisible should both be false
        assertFalse(myRooms[0].isVisible());
        assertFalse(myRooms[0].isAnswerable());
        // myNESWDoors should be true, true, true, true.
        assertTrue(myRooms[0].getHasNESWDoor(DoorDirection.NORTH));
        assertTrue(myRooms[0].getHasNESWDoor(DoorDirection.EAST));
        assertTrue(myRooms[0].getHasNESWDoor(DoorDirection.SOUTH));
        assertTrue(myRooms[0].getHasNESWDoor(DoorDirection.WEST));
        // both end and start points are false.
        assertFalse(myRooms[0].isEndpoint());

    }

    /**
     * Tests the prompt assignment of the constructor.
     */
    @Test
    public void testPromptAssignment() {
        // prompt assignment
        assertEquals("Is this true?", myQuestions[0].getPrompt());
        assertEquals("Which is B?", myQuestions[1].getPrompt());
        assertEquals("How true is this?", myQuestions[2].getPrompt());
    }

    /**
     * Tests whether all possible answers are shown for the multiple choice question.
     * Also serves to test the constructor for this aspect of the multiple choice question.
     */
    @Test
    public void testPossibleAnswers() {
        MultipleChoiceQuestion mult = (MultipleChoiceQuestion) myQuestions[1];
        List<String> test = mult.getPossibleAnswers();
        List<String> cust = new ArrayList<>();
        cust.add("A");
        cust.add("B");
        cust.add("C");
        cust.add("D");

        test.sort(String.CASE_INSENSITIVE_ORDER);
        cust.sort(String.CASE_INSENSITIVE_ORDER);

        assertEquals(cust, test);
    }

    /**
     * Tests the checkAnswer method in Room when it should be true
     * This also tests the checkAnswer method in Question.
     * It also serves to check the correct answer value as defined in the constructor of the Questions.
     */
    @Test
    public void testCorrectCheckAnswer() {
        assertTrue(myRooms[0].checkAnswer("true"));
        assertTrue(myRooms[1].checkAnswer("B"));
        assertTrue(myRooms[2].checkAnswer("vErY"));
    }

    /**
     * Tests the checkAnswer method in Room when it should be false
     * This also tests the checkAnswer method in Question.
     * It also serves to check the correct answer value as defined in the constructor of the Questions.
     */
    @Test
    public void testWrongCheckAnswer() {
        assertFalse(myRooms[0].checkAnswer("false"));
        assertFalse(myRooms[1].checkAnswer("c"));
        assertFalse(myRooms[2].checkAnswer("sorta"));
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