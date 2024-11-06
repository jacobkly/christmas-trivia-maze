package model;

public abstract class Question {

    private String myPrompt;

    public Question(String thePrompt) {
        this.myPrompt = thePrompt;
    }

    public String getPrompt() {
        return myPrompt;
    }

    /**
     * Checks if the answer is correct
     * the answer must be a string
     * returns a boolean
     *
     * @param theAnswer the answer that the user is inputting, as a string.
     * @return whether the answer was right or not.
     */
    public boolean checkAnswer(final String theAnswer) {
        return false;
    }
}
