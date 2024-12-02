package tests;

import model.*;
import model.RoomEnums.DoorDirection;
import model.RoomEnums.RoomArrayValues;
import model.RoomEnums.RoomInfo;
import model.RoomEnums.Visibility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.RoomEnums.ROOM_ARRAY_VALUES;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the model class through black box testing.
 */
class RoomBlackBoxTest {

    /** The questions to be tested. */
    private Question[] myQuestions;
    /** The rooms to be tested. */
    private Room[] myRooms;


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
        testInfo[RoomArrayValues.NORTH_DOOR.ordinal()] = RoomInfo.NORTH_CLOSED;
        testInfo[RoomArrayValues.EAST_DOOR.ordinal()] = RoomInfo.EAST_CLOSED;
        testInfo[RoomArrayValues.SOUTH_DOOR.ordinal()] = RoomInfo.SOUTH_CLOSED;
        testInfo[RoomArrayValues.WEST_DOOR.ordinal()] = RoomInfo.WEST_CLOSED;
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
        assertVisible(info[RoomArrayValues.ROOM_FILL.ordinal()]);
        // setting visibility to locked:
        myRooms[0].setVisibility(Visibility.LOCKED);
        info = myRooms[0].getRoomInfo();
        assertEquals(RoomInfo.LOCKED, info[RoomArrayValues.ROOM_FILL.ordinal()]);
        // setting visibility back to mystery:
        myRooms[0].setVisibility(Visibility.MYSTERY);
        info = myRooms[0].getRoomInfo();
        assertEquals(RoomInfo.MYSTERY, info[RoomArrayValues.ROOM_FILL.ordinal()]);
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
        testInfo[RoomArrayValues.ROOM_HIGHLIGHT.ordinal()] = RoomInfo.WITH_HIGHLIGHT;
        myRooms[0].setHigLig(true);
        RoomInfo[] info = myRooms[0].getRoomInfo();

        compareRoomInfo(info, testInfo);

        testInfo[RoomArrayValues.ROOM_HIGHLIGHT.ordinal()] = RoomInfo.NO_HIGHLIGHT;
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


}