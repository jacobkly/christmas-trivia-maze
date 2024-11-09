package controller;

import model.Maze;
import model.Question;
import view.MazeViewFrame;

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
    public void startResult() { myFrame.setResultScreen(); }
}
