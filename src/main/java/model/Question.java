package model;

/**
 * Models a Question in the maze.
 */
public class Question {
    /** The question to be asked */
    final private String myQuestion;
    /** The Correct answer to the question. */
    final private String myAnswer;
    /** The first option for an answer */
    final private String myChoiceA;
    /** The second option for an answer */
    final private String myChoiceB;
    /** The third option for an answer */
    final private String myChoiceC;
    /** The fourth option for an answer */
    final private String myChoiceD;
    /** Whether the question has been answered correctly */
    private boolean myIsAnswered;

    /**
     * A Question object with default values.
     *
     */
    public Question() {
        myQuestion = "Which one of the following is the correct answer?";
        myAnswer = "An option that looks suspicously correct";
        myChoiceA = "The first option to choose";
        myChoiceB = "A choice that could be correct";
        myChoiceC = "A choice that is probably wrong";
        myChoiceD = "An option that looks suspicously correct";

        myIsAnswered = false;
    }

    /**
     * Gets the question to be asked.
     *
     * @return the question to be asked.
     */
    public String getMyQuestion() {
        return myQuestion;
    }

    /**
     * Gets the first possible choice.
     *
     * @return the first possible choice.
     */
    public String getMyChoiceA() {
        return myChoiceA;
    }

    /**
     * Gets the second possible choice.
     *
     * @return the second possible choice.
     */
    public String getMyChoiceB() {
        return myChoiceB;
    }

    /**
     * Gets the third possible choice.
     *
     * @return the third possible choice.
     */
    public String getMyChoiceC() {
        return myChoiceC;
    }

    /**
     * Gets the fourth possible choice.
     *
     * @return the fourth possible choice.
     */
    public String getMyChoiceD() {
        return myChoiceD;
    }

    /**
     * Returns whether this question has been answered correctly.
     *
     * @return whether the question has been answered correctly.
     */
    public boolean isAnswered() {
        return myIsAnswered;
    }

    /**
     * Checks if the inputted answer is correct.
     * If it is, this updates the object accordingly.
     *
     * @param theAnswer the answer that is given
     * @return whether the answer was correct.
     */
    public boolean checkAnswer(String theAnswer) {
        if (theAnswer.equals(myAnswer)) {
            myIsAnswered = true;
            return true;
        } else {
            return false;
        }

    }
}
