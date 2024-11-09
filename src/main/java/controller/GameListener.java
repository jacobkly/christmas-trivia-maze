package controller;

public interface GameListener {

   void startMainMenu();

   void startPreparation();

   void startGame(int theNumRows, int theNumCols);

   void startResult();
}
