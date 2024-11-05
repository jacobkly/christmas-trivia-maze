package controller;

import view.MainMenuPanel;
import view.MazeScreenPanel;
import view.MazeViewFrame;

public class GameController implements GameListener{

    private MazeViewFrame myFrame;

    public GameController() {

    }

    public void setView(MazeViewFrame theFrame) {
        this.myFrame = theFrame;
        myFrame.setMainMenu();
    }

    @Override
    public void startPreparation() {
        myFrame.setPreparation();
    }

    @Override
    public void startGame() {
        myFrame.setMaze();
    }

}
