package controller;

import model.Maze;
import model.Player;
import model.Question;
import model.Room;
import view.MazeViewFrame;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Controls the logic and interactions of the game, coordinating between the view, player, and maze.
 *
 * @author Mathew Miller
 * @author Cai Spidel
 * @author Jacob Klymenko
 * @version 1.0
 */
public class GameController implements GameListener {

    /** The list of questions used in the game, fetched from the database. */
    private final List<Question> myQuestionList = new ArrayList<>(QuestionFactory.getQuestionsFromDatabase());

    /** The view frame used to display the maze and other UI components. */
    private MazeViewFrame myFrame;

    /** The maze representation of the game. */
    private Maze myMaze;

    /** The player instance representing the user's character and stats. */
    private Player myPlayer;

    /** Constructs a new GameController that does not initialize any components. */
    public GameController() { /* do nothing */ }

    /**
     * Sets the view frame for the game and starts the main menu.
     *
     * @param theFrame the view frame to set.
     */
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
        try {
            File file = new File(Objects.requireNonNull
                    (this.getClass().getResource("/savedGames/savedMaze.ser")).getPath());
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(myMaze);
            out.writeObject(myPlayer);
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
        Player player = null;
        try {
            // currently would produce error if the file does not exist.
            File file = new File(Objects.requireNonNull
                    (this.getClass().getResource("/savedGames/savedMaze.ser")).getPath());

            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            maze = (Maze) in.readObject();
            player = (Player) in.readObject();
            in.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException i) {
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
            myFrame.setMaze(myMaze);
        }

        return correct;
    }

    @Override
    public void startResult() {
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
        playerStats[2] = "Hints used: " + myPlayer.getHintsUsed() + " out of " + myPlayer.getMaxHintCount();
        playerStats[3] = "Rooms discovered: " + myPlayer.getRoomsDiscovered();
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
