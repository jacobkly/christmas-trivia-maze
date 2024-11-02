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
    final private String[] myChoices;

    /**
     * A Question object with default values.
     */
    public Question() {
        myQuestion = "Which one of the following is the correct answer?";
        myAnswer = "An option that looks suspicously correct";

        myChoices = new String[]{ "The first option to choose", "A choice that could be correct",
                "A choice that is probably wrong", "An option that looks suspicously correct" };
    }

    /**
     * Creates a question with the set parameters.
     *
     * @param theQuestion the question to be asked.
     * @param theAnswer the correct answer.
     * @param theChoices the possible answers to the question.
     */
    public Question(final String theQuestion, final String theAnswer, final String[] theChoices ) {
        myQuestion = theQuestion;
        myAnswer = theAnswer;
        myChoices = theChoices.clone();
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
     * Checks if the inputted answer is correct.
     * If it is, this updates the object accordingly.
     *
     * @param theAnswer the answer that is given
     * @return whether the answer was correct.
     */
    public boolean checkAnswer(final String theAnswer) {
        return theAnswer.equals(myAnswer);
    }

}
