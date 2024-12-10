package model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A factory class for creating and retrieving questions from a database. Supports multiple-choice,
 * boolean, and text-input question types.
 *
 * @author Mathew Miller
 * @author Jacob Klymenko (Javadoc)
 * @version 1.0
 */
public final class QuestionFactory {

    /**
     * Retrieves a list of questions from the database. The questions include multiple-choice,
     * boolean, and text-input types.
     *
     * @return a list of questions retrieved from the database
     */
    public static List<Question> getQuestionsFromDatabase() {
        List<Question> questionList = new ArrayList<>();

        String selectMultipleChoice = "SELECT question, answer, wrong_answer_1," +
                " wrong_answer_2, wrong_answer_3 FROM multiple_choice_question";
        String selectBoolean = "SELECT question, answer FROM boolean_question";
        String selectTextInput = "SELECT question, answer FROM text_input_question";

        File dbFile = new File("questions.sqlite");
        if (!dbFile.exists()) {
            try (InputStream in = QuestionFactory.class.getResourceAsStream("/questions.sqlite")) {
                if (in != null) {
                    Files.copy(in, dbFile.toPath());
                } else {
                    System.out.println("Database resource not found in JAR!");
                    return questionList;
                }
            } catch (final IOException theException) {
                theException.printStackTrace();
                return questionList;
            }
        }

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(selectMultipleChoice);
            while (rs.next()) {
                String prompt = rs.getString("question");
                String answer = rs.getString("answer");
                String wa1 = rs.getString("wrong_answer_1");
                String wa2 = rs.getString("wrong_answer_2");
                String wa3 = rs.getString("wrong_answer_3");

                List<String> wrongAnswers = new ArrayList<>();
                wrongAnswers.add(wa1);
                wrongAnswers.add(wa2);
                wrongAnswers.add(wa3);

                MultipleChoiceQuestion question = new MultipleChoiceQuestion(prompt, answer, wrongAnswers);
                questionList.add(question);
            }
            rs.close();

            rs = stmt.executeQuery(selectBoolean);
            while (rs.next()) {
                String prompt = rs.getString("question");
                boolean answer = rs.getBoolean("answer");

                BooleanQuestion question = new BooleanQuestion(prompt, answer);
                questionList.add(question);
            }
            rs.close();

            rs = stmt.executeQuery(selectTextInput);
            while (rs.next()) {
                String prompt = rs.getString("question");
                String answer = rs.getString("answer");

                TextInputQuestion question = new TextInputQuestion(prompt, answer);
                questionList.add(question);
            }
            rs.close();

        } catch (final SQLException theException) {
            System.out.println(theException.getMessage());
        }
        return questionList;
    }
}




