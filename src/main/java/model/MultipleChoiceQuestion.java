package model;

import java.util.ArrayList;
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

    public List<String> getPossibleAnswers() {
        List<String> result = new ArrayList<String>();
        result.add(myAnswer);
        result.addAll(myWrongAnswers);
        return result;
    }

    @Override
    public boolean checkAnswer(String theAnswer) {
        return myAnswer.equals(theAnswer);
    }
}
