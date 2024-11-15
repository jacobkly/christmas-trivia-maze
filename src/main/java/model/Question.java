package model;

import java.io.Serializable;

public abstract class Question implements Serializable {

    /** The serialVersionUID for this object. */
    private static final long serialVersionUID = 1L;

    private String myPrompt;

    public Question(String thePrompt) {
        this.myPrompt = thePrompt;
    }

    public String getPrompt() {
        return myPrompt;
    }
}
