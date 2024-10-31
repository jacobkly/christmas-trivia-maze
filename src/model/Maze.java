package model;

public class Maze {
    /** The 2D array of rooms in the maze. */
    private final Room[][] myRooms;

    /** The number of rows in the maze. */
    private final int myNumRows;

    /** The number of cols in the maze. */
    private final int myNumCols;

    /** The row for the starting room. */
    private final int myStartingRow;

    /** The col for the starting room. */
    private final int myStartingCol;

    /**
     * Creates a Maze that holds rooms.
     *
     * @param theRooms the rooms to create the maze.
     * @param theNumRows the number of rows in the maze.
     * @param theNumCols the number of cols in the maze.
     * @param theStartingRow the row for the starting room of the maze.
     * @param theStartingCol the col for the starting room of the maze.
     */
    public Maze(final Room[][] theRooms, final int theNumRows, final int theNumCols,
                final int theStartingRow, final int theStartingCol) {
        myRooms = theRooms;
        myNumRows = theNumRows;
        myNumCols = theNumCols;
        myStartingRow = theStartingRow;
        myStartingCol = theStartingCol;
    }

    /**
     * Gets the number of rows in the Maze.
     *
     * @return the number of rows in the maze.
     */
    public int getNumRows() {
        return myNumRows;
    }

    /**
     * Gets the number of cols in the Maze.
     *
     * @return the number of cols in the maze.
     */
    public int getNumCols() {
        return myNumCols;
    }

    /**
     * Gets the starting position of the maze.
     *
     * @return the two ints that make the starting position.
     */
    public int[] getStartingPos() {
        return new int[] {myStartingRow, myStartingCol};
    }

    /**
     * Gets the room from the position.
     *
     * @param theRow the row position.
     * @param theCol the col position.
     * @return the room in the position.
     */
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
        myRooms[theRow][theCol].isVisible();
        if (!myRooms[theRow][theCol].isVisible()) {
            myRooms[theRow][theCol].tryAnswer(theUserAnswer);
        }
    }

    /**
     * Carves a passage between two rooms.
     *
     * @param theRow1 the row number of the first room.
     * @param theCol1 the col number of the first room.
     * @param theRow2 the row number of the second room.
     * @param theCol2 the col number of the second room.
     */
    private void carveDoor(final int theRow1, final int theCol1, final int theRow2, final int theCol2) {

    }
}
