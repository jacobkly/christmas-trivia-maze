package view;

import controller.GameListener;
import model.BooleanQuestion;
import model.MultipleChoiceQuestion;
import model.Question;
import model.TextInputQuestion;

import javax.swing.*;
import java.awt.*;

public class QuestionPanel extends JPanel {

    public QuestionPanel(GameListener myGameListener) {

        setSize(400, 400);
        setBorder(new RoundedBorder(40));
        setBackground(Color.BLACK);
        setVisible(true);
    }

    public void setQuestion(Question theQuestion) {
        // Clear existing inputs
        // Update question text
        // Update panel visibility

        switch (theQuestion) {
            case MultipleChoiceQuestion m -> System.out.println("multi");
            case BooleanQuestion bq -> System.out.println("boolean");
            case TextInputQuestion tiq -> System.out.println("text");
            default -> throw new IllegalStateException("Unexpected question type");
        }
    }
}
