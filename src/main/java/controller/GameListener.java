package controller;

import model.Room;

public interface GameListener {

   void startMainMenu();

   void startPreparation();

   void startGame(String theName, int theNumRows, int theNumCols);

   boolean checkAnswer(String theAnswer);

   void startResult();

   void onRoomClicked(Room theRoom);
}
