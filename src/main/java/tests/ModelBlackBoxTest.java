package tests;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

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
        List<String> s = new ArrayList<String>();
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
    }

    /**
     * Tests the updateRoomVisibility method.
     */
    @Test
    public void testUpdateVisibility() {

    }

    /**
     * Tests the getRows and getCols methods.
     */
    @Test
    public void testGetRowsGetCols() {

    }

    /**
     * Tests the getCurrentlySelectedRoom functionality.
     */
    @Test
    public void testGetCurrentlySelectedRoom() {
        // use the question contained to check equivalence

    }

    /**
     * Tests the setRoomHigLig functionality.
     * Also tests the getCurrentlySelectedRoom functionality.
     */
    @Test
    public void testSetRoomHigLig() {

    }

    /**
     * Tests the getRoom method of the maze.
     */
    @Test
    public void testGetRoom() {
        // uses the question located in the room to test equivalence

    }

    /**
     * Tests that the maze constructor works correctly.
     */
    @Test
    public void testMazeConstructor() {
        // test that each room exists through the contained questions

        // test that a starting position exists (there is a landscape somewhere)

        // test that an ending position exists (iterate through the rooms)

        // test that borders of rooms are made properly
    }

    /**
     * Tests the door information capabilities of RoomInfo.
     */
    @Test
    public void testRoomDoorInfo() {
        // tests default with doors
        Room.RoomInfo[] testInfo = makeDefaultRoomInfo();
        Room.RoomInfo[] info = myRooms[0].getRoomInfo();
        compareRoomInfo(testInfo, info);

        // removes doors
        myRooms[0].setDoor(0, false);
        myRooms[0].setDoor(1, false);
        myRooms[0].setDoor(2, false);
        myRooms[0].setDoor(3, false);

        // tests that doors are gone
        testInfo[0] = Room.RoomInfo.NORTH_CLOSED;
        testInfo[1] = Room.RoomInfo.EAST_CLOSED;
        testInfo[2] = Room.RoomInfo.SOUTH_CLOSED;
        testInfo[3] = Room.RoomInfo.WEST_CLOSED;
        info = myRooms[0].getRoomInfo();
        compareRoomInfo(testInfo, info);
    }

    /**
     * Tests the fill capabilities of the RoomInfo.
     */
    @Test
    public void testRoomInfoFill() {
        // visibility is default MYSTERY
        Room.RoomInfo[] testInfo = makeDefaultRoomInfo();
        Room.RoomInfo[] info = myRooms[0].getRoomInfo();
        compareRoomInfo(testInfo, info);
        // setting visibility to visible:
        myRooms[0].setVisibility(Room.Visibility.VISIBLE);
        info = myRooms[0].getRoomInfo();
        assertVisible(info[4]);
        // setting visibility to locked:
        myRooms[0].setVisibility(Room.Visibility.LOCKED);
        info = myRooms[0].getRoomInfo();
        assertEquals(Room.RoomInfo.LOCKED, info[4]);
        // setting visibility back to mystery:
        myRooms[0].setVisibility(Room.Visibility.MYSTERY);
        info = myRooms[0].getRoomInfo();
        assertEquals(Room.RoomInfo.MYSTERY, info[4]);
    }

    /**
     * asserts that the RoomInfo is one of the visible types.
     *
     * @param theInfo the RoomInfo.
     */
    private void assertVisible(Room.RoomInfo theInfo) {
        assertNotEquals(theInfo, Room.RoomInfo.NORTH_OPEN);
        assertNotEquals(theInfo, Room.RoomInfo.NORTH_CLOSED);

        assertNotEquals(theInfo, Room.RoomInfo.EAST_OPEN);
        assertNotEquals(theInfo, Room.RoomInfo.EAST_CLOSED);

        assertNotEquals(theInfo, Room.RoomInfo.SOUTH_OPEN);
        assertNotEquals(theInfo, Room.RoomInfo.SOUTH_CLOSED);

        assertNotEquals(theInfo, Room.RoomInfo.WEST_OPEN);
        assertNotEquals(theInfo, Room.RoomInfo.WEST_CLOSED);


        assertNotEquals(theInfo, Room.RoomInfo.MYSTERY);
        assertNotEquals(theInfo, Room.RoomInfo.LOCKED);

        assertNotEquals(theInfo, Room.RoomInfo.NO_HIGHLIGHT);
        assertNotEquals(theInfo, Room.RoomInfo.WITH_HIGHLIGHT);
    }

    /**
     * Tests the roominfo of the highlighting capabilities.
     */
    @Test
    public void testRoomInfoHigLig() {
        Room.RoomInfo[] testInfo = makeDefaultRoomInfo();
        testInfo[5] = Room.RoomInfo.WITH_HIGHLIGHT;
        myRooms[0].setHigLig(true);
        Room.RoomInfo[] info = myRooms[0].getRoomInfo();

        compareRoomInfo(info, testInfo);

        testInfo[5] = Room.RoomInfo.NO_HIGHLIGHT;
        myRooms[0].setHigLig(false);
        info = myRooms[0].getRoomInfo();

        compareRoomInfo(info, testInfo);
    }

    /**
     * Tests the RoomInfo of the room.
     */
    @Test
    public void testRoomInfoDefault() {
        Room.RoomInfo[] testInfo = makeDefaultRoomInfo();
        Room.RoomInfo[] info = myRooms[0].getRoomInfo();
        compareRoomInfo(info, testInfo);
    }

    /**
     * Makes a default array of room information.
     *
     * @return a default room information array.
     */
    private Room.RoomInfo[] makeDefaultRoomInfo() {
        Room.RoomInfo[] result = new Room.RoomInfo[6];
        result[0] = Room.RoomInfo.NORTH_OPEN;
        result[1] = Room.RoomInfo.EAST_OPEN;
        result[2] = Room.RoomInfo.SOUTH_OPEN;
        result[3] = Room.RoomInfo.WEST_OPEN;
        result[4] = Room.RoomInfo.MYSTERY;
        result[5] = Room.RoomInfo.NO_HIGHLIGHT;
        return result;
    }

    /**
     * Compares the room info of two seperate room info arrays.
     *
     * @param theInfo the room being tested.
     * @param theTestInfo the test values to be used.
     */
    private void compareRoomInfo(Room.RoomInfo[] theInfo, Room.RoomInfo[] theTestInfo) {
        assertEquals(theInfo[0], theTestInfo[0]);
        assertEquals(theInfo[1], theTestInfo[1]);
        assertEquals(theInfo[2], theTestInfo[2]);
        assertEquals(theInfo[3], theTestInfo[3]);
        assertEquals(theInfo[4], theTestInfo[4]);
        assertEquals(theInfo[5], theTestInfo[5]);
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
        myRooms[0].setVisibility(Room.Visibility.VISIBLE);
        assertTrue(myRooms[0].isVisible());
        assertFalse(myRooms[0].isAnswerable());
        // setting visibility to locked:
        myRooms[0].setVisibility(Room.Visibility.LOCKED);
        assertFalse(myRooms[0].isVisible());
        assertTrue(myRooms[0].isAnswerable());
        // setting visibility back to mystery:
        myRooms[0].setVisibility(Room.Visibility.MYSTERY);
        assertFalse(myRooms[0].isVisible());
        assertFalse(myRooms[0].isAnswerable());
    }

    /**
     * Tests the setDoor methods in room.
     */
    @Test
    public void testSetDoor() {
        // tests default with doors
        assertTrue(myRooms[0].getHasNESWDoor(0));
        assertTrue(myRooms[0].getHasNESWDoor(1));
        assertTrue(myRooms[0].getHasNESWDoor(2));
        assertTrue(myRooms[0].getHasNESWDoor(3));

        // removes doors
        myRooms[0].setDoor(0, false);
        myRooms[0].setDoor(1, false);
        myRooms[0].setDoor(2, false);
        myRooms[0].setDoor(3, false);

        // tests that doors are gone
        assertFalse(myRooms[0].getHasNESWDoor(0));
        assertFalse(myRooms[0].getHasNESWDoor(1));
        assertFalse(myRooms[0].getHasNESWDoor(2));
        assertFalse(myRooms[0].getHasNESWDoor(3));
    }

    /**
     * Tests that sending invalid values to setDoor throws an exception.
     */
    @Test
    public void testSetDoorException() {
        assertThrows(IllegalArgumentException.class, () -> myRooms[0].setDoor(-1, true));
        assertThrows(IllegalArgumentException.class, () -> myRooms[0].setDoor(4, true));
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
        assertTrue(myRooms[0].getHasNESWDoor(0));
        assertTrue(myRooms[0].getHasNESWDoor(1));
        assertTrue(myRooms[0].getHasNESWDoor(2));
        assertTrue(myRooms[0].getHasNESWDoor(3));
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
        assertTrue(myRooms[0].checkAnswer("true")); ;
        assertTrue(myRooms[1].checkAnswer("B")); ;
        assertTrue(myRooms[2].checkAnswer("vErY")); ;
    }

    /**
     * Tests the checkAnswer method in Room when it should be false
     * This also tests the checkAnswer method in Question.
     * It also serves to check the correct answer value as defined in the constructor of the Questions.
     */
    @Test
    public void testWrongCheckAnswer() {
        assertFalse(myRooms[0].checkAnswer("false")); ;
        assertFalse(myRooms[1].checkAnswer("c")); ;
        assertFalse(myRooms[2].checkAnswer("sorta")); ;
    }

}