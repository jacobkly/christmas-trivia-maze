package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a question that can be asked.
 *
 * @author Mathew Miller
 * @author Cai Spidel
 * @version 1.0
 */
public abstract class Question implements Serializable {

    /** The serialVersionUID for this object. */
    @Serial
    private static final long serialVersionUID = 1L;

    /** The prompt of this question. */
    private final String myPrompt;

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

    /**
     * Gets the answer of an question
     *
     * @return the correct answer
     */
    public abstract String getAnswer();
}
