/*
 * TCSS 360 Autumn 2024
 * Course Project
 */
package model;

import java.util.Collections;
import java.util.List;

/**
 * Models multiple choice questions.
 *
 * @author Mathew Miller
 * @author Cai Spidel
 * @version 1.0
 */
public final class MultipleChoiceQuestion extends Question {
    /** The correct answer */
    private final String myAnswer;
    /** The list of possible answers */
    private final List<String> myPossibleAnswers; // myWrongAnswers + myAnswer

    /**
     * Constructs an multiple choice question
     *
     * @param thePrompt the prompt of the question
     * @param theAnswer the correct answer of the question
     * @param theWrongAnswers the incorrect answers of the question
     */
    public MultipleChoiceQuestion(
            final String thePrompt,
            final String theAnswer,
            final List<String> theWrongAnswers
    ) {
        super(thePrompt);
        myAnswer = theAnswer;
        myPossibleAnswers = theWrongAnswers;
        myPossibleAnswers.add(myAnswer);
        Collections.shuffle(myPossibleAnswers);
    }

    /**
     * Returns the possible answers to the question
     *
     * @return all the possible answers
     */
    public List<String> getPossibleAnswers() {
        // List<String> result = new ArrayList<>();
        // result.add(myAnswer);
        // result.addAll(myPossibleAnswers);
        return myPossibleAnswers;
    }

    @Override
    public boolean checkAnswer(String theAnswer) {
        return myAnswer.equals(theAnswer);
    }

    @Override
    public String getAnswer() {
        return myAnswer;
    }
}
