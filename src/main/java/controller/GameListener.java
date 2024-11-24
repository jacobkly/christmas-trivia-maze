package controller;

import model.Maze;
import model.Room;

/**
 * Listener interface for game-related events and actions.
 *
 * @author Mathew Miller
 * @author Cai Spidel
 * @author Jacob Klymenko
 * @version 1.0
 */
public interface GameListener {

   /** Starts the main menu view. */
   void startMainMenu();

   /** Starts the preparation phase of the game. */
   void startPreparation();

   /**
    * Initializes and starts the game with the given parameters.
    *
    * @param theNumRows      the number of rows in the maze.
    * @param theNumCols      the number of columns in the maze.
    * @param thePlayerName   the name of the player.
    * @param thePlayerMaxHealth the maximum health of the player.
    * @param thePlayerMaxHints  the maximum number of hints the player can use.
    */
   void startGame(
           int theNumRows,
           int theNumCols,
           String thePlayerName,
           int thePlayerMaxHealth,
           int thePlayerMaxHints
   );

   /**
    * Saves a game by serializing the maze and player representation.
    */
   void saveGame();

   /**
    * Resumes a saved game by loading a maze from a path.
    *
    * @return whether resuming the game succeeded.
    */
   boolean resumeGame();

   /**
    * Checks the player's answer to the current question.
    *
    * @param theAnswer the player's answer.
    * @return true if the answer is correct, false otherwise.
    */
   boolean checkAnswer(String theAnswer);

   /**
    * Starts the result screen, displaying the game's outcome.
    */
   void startResult(); // TODO this is likely unnecessary.

   /**
    * Handles a room click event.
    *
    * @param theRoom the room that was clicked.
    */
   void onRoomClicked(Room theRoom);

   /**
    * Retrieves the player's statistics.
    *
    * @return an array of strings containing player statistics.
    */
   String[] getPlayerStatistics(); // TODO This should not be here

   /**
    * Uses a hint to reveal the answer to the current question.
    */
   void useHint();
}
