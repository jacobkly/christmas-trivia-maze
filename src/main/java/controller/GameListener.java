package controller;

public interface GameListener {

   void startMainMenu();

   void startPreparation();

   void startGame(String theName, int theNumRows, int theNumCols);
   void startGame(int theNumRows, int theNumCols);

   void startResult();
}
