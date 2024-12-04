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
 *
 * @author Cai Spidel
 * @version 1.0
 */
public final class QuestionBlackBoxTest {

    /** The questions to be tested. */
    private Question[] myQuestions;

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
}