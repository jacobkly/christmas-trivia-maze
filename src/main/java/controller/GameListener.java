package controller;

import model.Maze;
import model.Room;

public interface GameListener {

   void startMainMenu();

   void startPreparation();

   void startGame(String theName, int theNumRows, int theNumCols);

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

   void startResult();

   void onRoomClicked(Room theRoom);
}
