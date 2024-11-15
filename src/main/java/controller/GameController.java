package controller;

import model.*;
import view.MazeViewFrame;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    public void saveGame(final Maze theMaze) {
        try {
            File file = new File(Objects.requireNonNull
                    (this.getClass().getResource("/savedGames/savedMaze.ser")).getPath());
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(theMaze);
            out.close();
            fileOut.close();
            System.out.print("Serialized data is saved in /savedGames/savedMaze.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @Override
    public void resumeGame() {
        Maze maze = null;
        try {
            // currently would produce error if the file does not exist.
            File file = new File(Objects.requireNonNull
                    (this.getClass().getResource("/savedGames/savedMaze.ser")).getPath());

            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            maze = (Maze) in.readObject();
            in.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Maze class not found");
            c.printStackTrace();
            return;
        }

        myMaze = maze;
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
