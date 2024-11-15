package controller;

import model.*;
import view.MazeViewFrame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController implements GameListener{

    private final List<Question> myQuestionList = new ArrayList<>(QuestionFactory.getQuestionsFromDatabase());

    private MazeViewFrame myFrame;

    private Maze myMaze;

    private Player myPlayer;

    public GameController() { /* do nothing */ }

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
    public void startGame(int theNumRows, int theNumCols, final String thePlayerName, final int thePlayerMaxHealth) {
        List<Question> questions = new ArrayList<>(myQuestionList);
        Collections.shuffle(questions);
        myMaze = new Maze(questions, theNumRows, theNumCols);
        myFrame.setMaze(myMaze);

        myPlayer = new Player(thePlayerName, thePlayerMaxHealth);
        System.out.println("Player name: " + myPlayer.getName());
        System.out.println("Player health given: " + thePlayerMaxHealth);
        System.out.println("Player health received: " + myPlayer.getHealthCount());
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
            if (selectedRoom.isEndpoint()) {
                System.out.println("--- PLAYER REACHED END POINT ---");
                myFrame.updatePlayerResult(true);
                startResult();
                return true;
            }
            selectedRoom.setVisibility(0);
        } else {
            // Wrong answer, remove 1 life
            if (myPlayer.getHealthCount() == 0) {
                myFrame.updatePlayerResult(false);
                startResult();
                return false;
            }
        }

        myFrame.setMaze(myMaze);
        return correct;
    }

    @Override
    public void startResult() {
        /* to test result screens */
//        myFrame.updatePlayerResult(true);
//        myFrame.updatePlayerResult(false);
        /* ---------------------- */
        myFrame.setResultScreen();
    }

    @Override
    public void onRoomClicked(final Room theRoom) {
        Room selectedRoom = myMaze.getCurrentlySelectedRoom();
        if (selectedRoom != null) {
            selectedRoom.setHigLig(false);
        }

        theRoom.setHigLig(true);
        myFrame.setMaze(myMaze);
    }

    @Override
    public String[] getPlayerStatistics() {
        String[] playerStats = new String[4]; // four main stats
        playerStats[0] = "Player name: " + myPlayer.getName();
        playerStats[1] = "Health left: " + myPlayer.getHealthCount() + " out of " + myPlayer.getMaxHealthCount();
        /* last two elements won't be used, they are just an example */
        playerStats[2] = "Hints left: " + myPlayer.getHintsUsed() + " out of " + myPlayer.getMaxHintCount();
        playerStats[3] = "Rooms discovered: " + myPlayer.getRoomsDiscovered();
        /* --------------------------------------------------------- */
        return playerStats;
    }
}
