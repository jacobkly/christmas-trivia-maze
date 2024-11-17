package model;

public class TextInputQuestion extends Question {

    private String myAnswer;

    public TextInputQuestion(final String thePrompt, final String theAnswer) {
        super(thePrompt);
        myAnswer = theAnswer;
    }

    @Override
    public boolean checkAnswer(String theAnswer) {
        return myAnswer.equalsIgnoreCase(theAnswer);
    }
}
