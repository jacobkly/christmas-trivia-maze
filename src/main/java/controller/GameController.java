package controller;

import model.*;
import view.MazeViewFrame;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GameController implements GameListener {

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
    public void startGame(
            int theNumRows,
            int theNumCols,
            final String thePlayerName,
            final int thePlayerMaxHealth,
            final int thePlayerMaxHints
    ) {
        List<Question> questions = new ArrayList<>(myQuestionList);
        Collections.shuffle(questions);
        myMaze = new Maze(questions, theNumRows, theNumCols);
        myFrame.setMaze(myMaze);

        myPlayer = new Player(thePlayerName, thePlayerMaxHealth, thePlayerMaxHints);
        myFrame.setPlayer(myPlayer);
        System.out.println("Player name: " + myPlayer.getName());
        System.out.println("Player health given: " + thePlayerMaxHealth);
        System.out.println("Player health received: " + myPlayer.getHealthCount());
    }

    @Override
    public void saveGame() {
        SerialWrapper wrapper = new SerialWrapper(myPlayer, myMaze);
        try {
            FileOutputStream fileOut = new FileOutputStream("saveGame.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(wrapper);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in savegame.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @Override
    public boolean resumeGame() {
        SerialWrapper wrapper = null;
        boolean success = false;
        try {
            FileInputStream fileIn = new FileInputStream("savegame.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            wrapper = (SerialWrapper) in.readObject();
            in.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found, there is no saved game.");
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("SerialWrapper class not found");
            c.printStackTrace();
        }
        if(wrapper != null) {
            myMaze = wrapper.getMaze();
            myPlayer = wrapper.getPlayer();
            myFrame.setMaze(myMaze);
            myFrame.setPlayer(myPlayer);
            success = true;
        }
        return success;
    }

    @Override
    public boolean checkAnswer(final String theAnswer) {
        boolean correct;
        Room selectedRoom = myMaze.getCurrentlySelectedRoom();
        correct = selectedRoom.checkAnswer(theAnswer);

        if (correct) {
            if (selectedRoom.isEndpoint()) {
                System.out.println("--- PLAYER REACHED END POINT ---");
                myFrame.updatePlayerResult(true);
                startResult();
                return true;
            }
        } else {
            myPlayer.setHealthCount(myPlayer.getHealthCount() - 1);
            myFrame.setPlayer(myPlayer);
        }
        // TODO merge result screen for win or loss into one method.
        if (myPlayer.getHealthCount() == 0) {
            myFrame.updatePlayerResult(false);
            myFrame.setResultScreen();

        } else {
            saveGame();

            myFrame.setMaze(myMaze);
        }

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
        playerStats[2] = "Hints used: " + myPlayer.getHintsUsed() + " out of " + myPlayer.getMaxHintCount();
        playerStats[3] = "Rooms discovered: " + myPlayer.getRoomsDiscovered();
        /* --------------------------------------------------------- */
        return playerStats;
    }

    @Override
    public void useHint() {
        if (myPlayer.getHints() > 0) {
            myPlayer.setHints(myPlayer.getHints() - 1);
            myFrame.setPlayer(myPlayer);
            checkAnswer(myMaze.getCurrentlySelectedRoom().getQuestion().getAnswer());
        }
    }
}
