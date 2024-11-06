package view;

import controller.GameListener;
import model.BooleanQuestion;
import model.MultipleChoiceQuestion;
import model.Question;
import model.TextInputQuestion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionPanel extends JPanel {

    private final JLabel myQuestionPrompt = new JLabel();

    public QuestionPanel(GameListener myGameListener) {

        setSize(400, 400);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new RoundedBorder(40));
        setBackground(Color.BLACK);
        setVisible(true);
    }

    public void setQuestion(Question theQuestion) {

        List<String> answers = new ArrayList<>();

        myQuestionPrompt.setFont(Fonts.getPixelFont(12));
        myQuestionPrompt.setText(theQuestion.getPrompt());
        // Clear existing inputs
        // Update question text
        // Update panel visibility

        switch (theQuestion) {
            case MultipleChoiceQuestion m -> answers.add(m.getAnswer());
            case BooleanQuestion bq -> System.out.println("boolean");
            case TextInputQuestion tiq -> System.out.println("text");
            default -> throw new IllegalStateException("Unexpected question type");
        }
    }
}
