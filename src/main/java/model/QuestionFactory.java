package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionFactory {

    public static List<Question> getQuestionsFromDatabase() {
        List<Question> questionList = new ArrayList<>();

        String selectMultipleChoice = "SELECT question, answer, wrong_answer_1," +
                " wrong_answer_2, wrong_answer_3 FROM multiple_choice_question";
        String selectBoolean = "SELECT question, answer FROM boolean_question";
        String selectTextInput = "SELECT question, answer FROM text_input_question";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:questions.sqlite");
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

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return questionList;
    }
}




