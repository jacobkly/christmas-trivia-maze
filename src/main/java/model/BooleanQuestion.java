package model;

public class BooleanQuestion extends Question {

    private final boolean myAnswer;

    public BooleanQuestion(final String thePrompt, final boolean theAnswer) {
        super(thePrompt);
        myAnswer = theAnswer;
    }

    @Override
    public boolean checkAnswer(String theAnswer) {
        return myAnswer == Boolean.parseBoolean(theAnswer);
    }

    @Override
    public String getAnswer() {
        return Boolean.toString(myAnswer);
    }
}
