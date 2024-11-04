package model;

public class BooleanQuestion extends Question {

    private boolean answer;

    public BooleanQuestion(final String thePrompt, final boolean theAnswer) {
        super(thePrompt);
        answer = theAnswer;
    }

    public boolean isAnswer() {
        return answer;
    }
}
