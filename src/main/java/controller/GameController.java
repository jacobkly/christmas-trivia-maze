package controller;

import model.*;
import view.MazeViewFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controls the logic and interactions of the game, coordinating between the view, player, and maze.
 *
 * @author Mathew Miller
 * @author Cai Spidel
 * @author Jacob Klymenko
 * @version 1.0
 */
public class GameController implements GameListener {

    /**
     * The list of questions used in the game, fetched from the database.
     */
    private final List<Question> myQuestionList =
            new ArrayList<>(QuestionFactory.getQuestionsFromDatabase());

    /**
     * The view frame used to display the maze and other UI components.
     */
    private MazeViewFrame myFrame;

    /**
     * The maze representation of the game.
     */
    private Maze myMaze;

    /**
     * The player instance representing the user's character and stats.
     */
    private Player myPlayer;

    /**
     * Tracks the number of rooms the player has discovered.
     */
    private int myRoomsDiscovered;

    private boolean myDebugSelected;

    /**
     * Constructs a new GameController and does not perform any initialization.
     */
    public GameController() { /* do nothing */ }

    /**
     * Filechooser to choose locations to save and load files
     */
    private final JFileChooser myFileChooser = new JFileChooser("./src/main/resources/saveFiles");


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
        myRoomsDiscovered = 0;

        myPlayer = new Player(thePlayerName, thePlayerMaxHealth, thePlayerMaxHints);
        myFrame.setPlayer(myPlayer);
    }

    @Override
    public void saveGame() {
        SerialWrapper wrapper = new SerialWrapper(myPlayer, myMaze);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Save Files", "ser");
        myFileChooser.setFileFilter(filter);
        final int result = myFileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                FileOutputStream fileOut = new FileOutputStream(myFileChooser.getSelectedFile());
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(wrapper);
                out.close();
                fileOut.close();
                System.out.println("Serialized data is saved in savegame.ser");
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
    }

    @Override
    public boolean loadGame() {
        SerialWrapper wrapper = null;
        boolean success = false;
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Save Files", "ser");
        myFileChooser.setFileFilter(filter);
        final int result = myFileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                FileInputStream fileIn = new FileInputStream(myFileChooser.getSelectedFile());
                ObjectInputStream in = new ObjectInputStream(fileIn);
                wrapper = (SerialWrapper) in.readObject();
                in.close();
                fileIn.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found, there is no saved game.");
            } catch (IOException i) {
                JOptionPane.showMessageDialog(null,
                        "The selected file is not a save game!", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException c) {
                System.out.println("SerialWrapper class not found");
                c.printStackTrace();
            }
            if (wrapper != null) {
                myMaze = wrapper.getMaze();
                myPlayer = wrapper.getPlayer();
                myFrame.setMaze(myMaze);
                myFrame.setPlayer(myPlayer);
                success = true;
            }
        }
        return success;
    }

    @Override
    public boolean checkAnswer(final String theAnswer) {
        boolean correct;
        Room selectedRoom = myMaze.getCurrentlySelectedRoom();
        correct = selectedRoom.checkAnswer(theAnswer);

        if (correct) {
            myPlayer.setRoomsDiscovered(++myRoomsDiscovered);

            try {
                myFrame.playSoundEffect(true);
            } catch (Exception theE) {
                throw new RuntimeException(theE);
            }

            if (selectedRoom.isEndpoint()) {
                myFrame.setResult(true, myPlayer.getPlayerStatistics());
                myRoomsDiscovered = 0;
                return true;
            }
        } else {
            try {
                myFrame.playSoundEffect(false);
            } catch (Exception theE) {
                throw new RuntimeException(theE);
            }
            myPlayer.setHealth(myPlayer.getHealth() - 1);
            myFrame.setPlayer(myPlayer);
        }

        if (myPlayer.getHealth() == 0) {
            myFrame.setResult(false, myPlayer.getPlayerStatistics());

        } else {

            myFrame.setMaze(myMaze);
        }

        return correct;
    }

    @Override
    public void onRoomClicked(final Room theRoom) {
        // Don't allow selecting mystery rooms
        if (theRoom.isMystery()) {
            return;
        }

        // Clear existing selected room
        Room selectedRoom = myMaze.getCurrentlySelectedRoom();
        if (selectedRoom != null) {
            selectedRoom.setHigLig(false);
        }

        // Set the newly selected room
        theRoom.setHigLig(true);
        myMaze.setSelectedRoom(theRoom);

//      // Update the UI
        myFrame.setHintEnabled(myPlayer.getHints() > 0 && theRoom.isAnswerable() && theRoom.getQuestion() != null);
        myFrame.setMaze(myMaze);
    }

    @Override
    public void useHint() {
        if (myDebugSelected) {
            checkAnswer(myMaze.getCurrentlySelectedRoom().getQuestion().getAnswer());
        } else {
            if (myPlayer.getHints() > 0) {
                myPlayer.setHints(myPlayer.getHints() - 1);
                myFrame.setPlayer(myPlayer);
                checkAnswer(myMaze.getCurrentlySelectedRoom().getQuestion().getAnswer());
            }
            myFrame.setHintEnabled(false);
        }
    }

    @Override
    public void debugIsSelected(final boolean theBoolean) {
        myDebugSelected = theBoolean;
    }

}
