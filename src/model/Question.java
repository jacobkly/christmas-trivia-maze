package model;

/**
 * Models a Question in the maze.
 */
public class Question {
    /** The question to be asked */
    final private String myQuestion;
    /** The Correct answer to the question. */
    final private String myAnswer;
    /** The different possible answers to the question. */
    final private String myChoices[];
    /** Whether the question has been answered correctly */
    private boolean myIsAnswered;

    /**
     * A Question object with default values.
     */
    public Question() {
        myQuestion = "Which one of the following is the correct answer?";
        myAnswer = "An option that looks suspicously correct";

        myChoices = new String[]{ "The first option to choose", "A choice that could be correct",
                "A choice that is probably wrong", "An option that looks suspicously correct" };

        myIsAnswered = false;
    }

    /**
     * Creates a question with the set parameters.
     *
     * @param theQuestion the question to be asked.
     * @param theAnswer the correct answer.
     * @param theChoices the possible answers to the question.
     * @param theIsAnswered whether the room starts answered or not.
     */
    public Question(String theQuestion, String theAnswer, String[] theChoices, boolean theIsAnswered ) {
        myQuestion = theQuestion;
        myAnswer = theAnswer;
        myChoices = theChoices.clone();
        myIsAnswered = theIsAnswered;
    }

    /**
     * Gets the question to be asked.
     *
     * @return the question to be asked.
     */
    public String getMyQuestion() { return myQuestion; }

    /**
     * Gets the first possible choice.
     *
     * @return the first possible choice.
     */
    public String[] getMyChoices() { return myChoices; }

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
