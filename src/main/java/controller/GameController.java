package controller;

import model.*;
import view.GamePanel;
import view.MazeViewFrame;
import view.QuestionPanel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController implements GameListener{

    private MazeViewFrame myFrame;
    private Maze myMaze;
    private List<Question> myQuestionList = new ArrayList<>(QuestionFactory.getQuestionsFromDatabase());
    public GameController() {

    }

    public void setView(MazeViewFrame theFrame) {
        this.myFrame = theFrame;
        startMainMenu();
    }

    @Override
    public void startMainMenu() {
        myFrame.setMainMenu();
    }

    @Override
    public void startPreparation() {
        myFrame.setPreparation();
    }

    @Override
    public void startGame(String theName, int theNumRows, int theNumCols) {
        List<Question> questions = new ArrayList<>(myQuestionList);
        Collections.shuffle(questions);
        myMaze = new Maze(questions, theNumRows, theNumCols);
        myFrame.setMaze(myMaze);
    }

    @Override
    public boolean checkAnswer(final String theAnswer) {
        Room selectedRoom = myMaze.getCurrentlySelectedRoom();
        Question question = selectedRoom.getQuestion();


        boolean correct;
        switch (question) {
            case MultipleChoiceQuestion m -> correct = m.getAnswer().equals(theAnswer);
            case TextInputQuestion tiq -> correct = tiq.getAnswer().equalsIgnoreCase(theAnswer);
            case BooleanQuestion bq -> correct = bq.isAnswer() == Boolean.parseBoolean(theAnswer);
            default -> throw new IllegalStateException("Unexpected question type");
        }

        if (correct) {
            // Right answer, mark the room complete
            selectedRoom.setVisibility(0);
        } else {
            // Wrong answer, lock the room
            selectedRoom.setVisibility(1);
        }


        myFrame.setMaze(myMaze);
        return correct;
    }

    @Override
    public void startResult() { myFrame.setResultScreen(); }

    @Override
    public void onRoomClicked(final Room theRoom) {
        Room selectedRoom = myMaze.getCurrentlySelectedRoom();
        if (selectedRoom != null) {
            selectedRoom.setHigLig(false);
        }

        theRoom.setHigLig(true);
        myFrame.setMaze(myMaze);
    }


}
