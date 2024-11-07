package model;

public class TextInputQuestion extends Question {

    private String myAnswer;

    public TextInputQuestion(final String thePrompt, final String theAnswer) {
        super(thePrompt);
        myAnswer = theAnswer;
    }

    public String getAnswer() {
        return myAnswer;
    }
}
