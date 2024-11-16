package model;

public class BooleanQuestion extends Question {

    private boolean answer;

    public BooleanQuestion(final String thePrompt, final boolean theAnswer) {
        super(thePrompt);
        answer = theAnswer;
    }

    @Override
    public boolean checkAnswer(String theAnswer) {
        return answer == Boolean.parseBoolean(theAnswer);
    }
}
