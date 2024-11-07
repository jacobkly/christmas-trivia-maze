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
    public void startGame(int theNumRows, int theNumCols) {
        // here make start/end
        int startRow = 1;
        int startCol = 1;
        int endRow = 3;
        int endCol = 4;

        List<Question> questions = new ArrayList<>(myQuestionList);
        Collections.shuffle(questions);
        myMaze = new Maze(questions, theNumRows, theNumCols, startRow, startCol, endRow, endCol);
        myFrame.setMaze(myMaze);

    }



}
