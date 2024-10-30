package controller;

import view.MainMenuPanel;
import view.MazeScreenPanel;
import view.MazeViewFrame;

public class GameController implements GameListener{

    private MazeViewFrame myFrame;

    public GameController() {


    }

    public void setView(MazeViewFrame myFrame) {

        this.myFrame = myFrame;
    }


    @Override
    public void startGame() {
        myFrame.setMaze();

    }

}
