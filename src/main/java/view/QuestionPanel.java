package view;

import controller.GameListener;
import model.BooleanQuestion;
import model.MultipleChoiceQuestion;
import model.Question;
import model.TextInputQuestion;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuestionPanel extends JPanel {

    private final GameListener myGameListener;
    private final JTextArea myQuestionPrompt = new JTextArea();
    private JPanel myAnswerPanel;

    public QuestionPanel(GameListener myGameListener) {
        this.myGameListener = myGameListener;
        myQuestionPrompt.setEditable(false);
        myQuestionPrompt.setLineWrap(true);
        myQuestionPrompt.setWrapStyleWord(true);
        myQuestionPrompt.setBackground(Color.BLACK);
        myQuestionPrompt.setFont(Fonts.getPixelFont(14));
        myQuestionPrompt.setForeground(Color.WHITE);

        setSize(400, 400);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new RoundedBorder(40));
        setBackground(Color.BLACK);
        setVisible(true);

        myQuestionPrompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(myQuestionPrompt);

        setQuestion(new MultipleChoiceQuestion(
                "Who sang the hit Christmas single 'Last Christmas'?",
                "Bitch you know what",
                List.of("Yall Ugly",
                        "Sommin",
                        "Sommin Else")));
    }

    public void setQuestion(Question theQuestion) {
        myQuestionPrompt.setText(theQuestion.getPrompt());
        // Clear existing inputs
        // Update panel visibility

        if (myAnswerPanel != null) {
            remove(myAnswerPanel);
        }

        switch (theQuestion) {
            case MultipleChoiceQuestion m -> myAnswerPanel = new MultipleChoiceQuestionPanel(myGameListener, m);
            case BooleanQuestion bq -> System.out.println("boolean");
            case TextInputQuestion tiq -> System.out.println("text");
            default -> throw new IllegalStateException("Unexpected question type");
        }

        this.add(myAnswerPanel);
    }

    private class MultipleChoiceQuestionPanel extends JPanel {
        public MultipleChoiceQuestionPanel(GameListener theGameListener, MultipleChoiceQuestion theQuestion) {
            setBackground(Color.BLACK);
        }
    }
}
