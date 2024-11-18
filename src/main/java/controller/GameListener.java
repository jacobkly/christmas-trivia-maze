package controller;

import model.Maze;
import model.Room;

public interface GameListener {

   void startMainMenu();

   void startPreparation();

   void startGame(
           int theNumRows,
           int theNumCols,
           String thePlayerName,
           int thePlayerMaxHealth,
           int thePlayerMaxHints
   );

   /**
    * Saves a game by serializing the maze representation.
    *
    * @param theMaze the maze of the game to be saved.
    */
   void saveGame(final Maze theMaze);

   /**
    * Resumes a saved game by loading a maze from a path.
    */
   void resumeGame();

   boolean checkAnswer(String theAnswer);

   void startResult(); // TODO this is likely unnecessary.

   void onRoomClicked(Room theRoom);

   String[] getPlayerStatistics(); // TODO This should not be here

   void useHint();

}
