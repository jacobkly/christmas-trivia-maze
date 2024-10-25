package model;

public class Maze {

    private final Room[][] myRooms;

    private final int myNumRows;

    private final int myNumCols;

    private final int myStartingRow;

    private final int myStartingCol;

    public Maze(final Room[][] theRooms, final int theNumRows, final int theNumCols,
                final int theStartingRow, final int theStartingCol) {
        myRooms = theRooms;
        myNumRows = theNumRows;
        myNumCols = theNumCols;
        myStartingRow = theStartingRow;
        myStartingCol = theStartingCol;
    }

    public int getNumRows() {
        return myNumRows;
    }

    public int getNumCols() {
        return myNumCols;
    }

    public int[] getStartingPos() {
        return new int[] {myStartingRow, myStartingCol};
    }

    // instead of selectionRoom(int, int)
    public Room getRoom(final int theRow, final int theCol) {
        return myRooms[theRow][theCol];
    }

    public int[][] getNearPassages(final int theRow, final int theCol) {
        int[][] adjacentPassages = new int[4][2];
        int[][] directions = new int[][] {{theRow - 1, theCol},
                {theRow, theCol + 1},
                {theRow + 1, theCol},
                {theRow, theCol - 1},};

        for (int i = 0; i < directions.length; i++) {
            int row = directions[i][0];
            int col = directions[i][1];

            if (myRooms[row][col].isVisible()) {
                adjacentPassages[i] = new int[] {row, col};
            }
        }
        return adjacentPassages;
    }

    public boolean hasNearPassages(final int theRow, final int theCol) {
        return getNearPassages(theRow, theCol).length > 0;
    }

    // will most likely need to refactor maze, room, and question class to avoid coupling
    public void updateVisibility(final int theRow, final int theCol,
                                 final String theUserAnswer) {
        if (!myRooms[theRow][theCol].isVisible()) {
            myRooms[theRow][theCol].getQuestion().checkAnswer(theUserAnswer);
        }
    }
}
