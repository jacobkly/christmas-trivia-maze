package controller;

import view.MainMenuPanel;
import view.MazeScreenPanel;
import view.MazeViewFrame;

public class GameController implements GameListener{

    private final MazeViewFrame myFrame;
    private final MazeScreenPanel myScreenPanel;
    private final MainMenuPanel myMainMenuPanel;

    public GameController(MazeViewFrame myFrame, MazeScreenPanel myScreenPanel, MainMenuPanel myMainMenuPanel) {


        this.myFrame = myFrame;
        this.myScreenPanel = myScreenPanel;
        this.myMainMenuPanel = myMainMenuPanel;
    }

    @Override
    public void startGame() {
        myMainMenuPanel.setVisible(false);
        myScreenPanel.setVisible(true);
    }
}
