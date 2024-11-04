package model;

public abstract class Question {

    private String myPrompt;

    public Question(String thePrompt) {
        this.myPrompt = thePrompt;
    }

    public String getPrompt() {
        return myPrompt;
    }
}
