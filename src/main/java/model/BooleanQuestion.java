/*
 * TCSS 360 Autumn 2024
 * Course Project
 */
package model;

/**
 * Represents a boolean question that can be asked.
 *
 * @author Mathew Miller
 * @author Cai Spidel
 * @version 1.0
 */
public final class BooleanQuestion extends Question {
    /** The correct answer */
    private final boolean myAnswer;

    /**
     * Constructs a boolean choice question.
     *
     * @param thePrompt the prompt of the question
     * @param theAnswer the correct answer to the question
     */
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
