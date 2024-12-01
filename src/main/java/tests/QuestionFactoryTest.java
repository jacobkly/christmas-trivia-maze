package tests;

import controller.QuestionFactory;
import model.Question;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class QuestionFactoryTest {

    @Test
    void getQuestionsFromDatabase() {
        List<Question> questionsFromDatabase = QuestionFactory.getQuestionsFromDatabase();
        assertNotNull(questionsFromDatabase);
        assertEquals(35, questionsFromDatabase.size());
        Optional<Question> question = questionsFromDatabase.stream()
                .filter(q -> q.getPrompt().equalsIgnoreCase(
                        "In which year was the first Christmas card sent?")
                )
                .findFirst();
        assertTrue(question.isPresent());
    }

}