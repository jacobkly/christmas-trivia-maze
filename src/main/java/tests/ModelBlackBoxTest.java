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
     * Tests the image strings of the room class.
     */
    @Test
    public void testImageStrings() {

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