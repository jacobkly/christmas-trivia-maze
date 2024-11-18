package model;

import java.io.Serializable;

/**
 * Represents a question that can be asked.
 */
public abstract class Question implements Serializable {

    /** The serialVersionUID for this object. */
    private static final long serialVersionUID = 1L;

    /** The prompt of this question. */
    private String myPrompt;

    /**
     * Constructs a question.
     *
     * @param thePrompt the prompt of this question.
     */
    public Question(String thePrompt) {
        this.myPrompt = thePrompt;
    }

    /**
     * Gets the prompt of this question.
     *
     * @return the prompt of the question.
     */
    public String getPrompt() {
        return myPrompt;
    }

    /**
     * Checks the answer of the question to see if the input is correct.
     *
     * @param theAnswer a string representation of the inputted answer.
     * @return whether the answer was correct or not.
     */
    public abstract boolean checkAnswer(String theAnswer);

    public abstract String getAnswer();
}
