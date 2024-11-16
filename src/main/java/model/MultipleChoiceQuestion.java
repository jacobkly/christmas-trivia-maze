package model;

import java.util.List;

public class MultipleChoiceQuestion extends Question {

    private String myAnswer;
    private List<String> myWrongAnswers;

    public MultipleChoiceQuestion(
            final String thePrompt,
            final String theAnswer,
            final List<String> theWrongAnswers
    ) {
        super(thePrompt);
        myAnswer = theAnswer;
        myWrongAnswers = theWrongAnswers;
    }

    public String getAnswer() {
        return myAnswer;
    }

    public List<String> getWrongAnswers() {
        return myWrongAnswers;
    }

    @Override
    public boolean checkAnswer(String theAnswer) {
        return getAnswer().equals(theAnswer);
    }
}
