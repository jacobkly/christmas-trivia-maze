package controller;

import model.Room;

public interface GameListener {

   void startMainMenu();

   void startPreparation();

   void startGame(int theNumRows, int theNumCols, final String thePlayerName, final int thePlayerMaxHealth);

   boolean checkAnswer(String theAnswer);

   void startResult();

   void onRoomClicked(Room theRoom);
}
