/*
 * TCSS 360 Autumn 2024
 * Course Project
 */
package model;

/**
 * Represents a text input question that can be asked.
 *
 * @author Mathew Miller
 * @author Cai Spidel
 * @version 1.0
 */
public final class TextInputQuestion extends Question {
    /** The correct answer */
    private final String myAnswer;

    /**
     * Constructs an text input question
     *
     * @param thePrompt the prompt of the question
     * @param theAnswer the answer to the question
     */
    public TextInputQuestion(final String thePrompt, final String theAnswer) {
        super(thePrompt);
        myAnswer = theAnswer;
    }

    @Override
    public boolean checkAnswer(String theAnswer) {
        return myAnswer.equalsIgnoreCase(theAnswer);
    }

    @Override
    public String getAnswer() {
        return myAnswer;
    }
}
