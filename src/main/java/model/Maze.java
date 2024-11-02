package model;

public class Maze {
    /** The 2D array of rooms in the maze. */
    private final Room[][] myRooms;

    /** The position of the starting row and starting col. */
    private final int[] myStartingRowCol;

    /** The position of the ending row and ending col. */
    private final int[] myEndingRowCol;

    private final int[] myCurrentRoomHigLig = new int[] {0, 0};

    /**
     * Creates a Maze that manages rooms.
     *
     * @param theRooms the rooms to create the maze.
     * @param theStartingRow the row for the starting room of the maze.
     * @param theStartingCol the col for the starting room of the maze.
     */
    public Maze(final int theRows, final int theColumns, final int theStartingRow, final int theStartingCol,
                final int theEndingRow, final int theEndingCol) {
        Room[][] theRooms = new Room[theRows][theColumns];
        for(int i = 0; i < theRows; i++) {
            for(int j = 0; j < theColumns; j++) {
                // would use a room factory here
                Room room = new Room();
                theRooms[i][j] = room;
            }
        }

        myRooms = theRooms;

        myStartingRowCol = new int[] {theStartingRow, theStartingCol};

        myEndingRowCol = new int[] {theEndingRow, theEndingCol};

        mazeFirstSetup();

        setRoomHigLig(myStartingRowCol[0], myStartingRowCol[1]);
    }

    /**
     * Sets the starting and ending rooms to the correct state.
     */
    private void mazeFirstSetup() {
        getRoom(myStartingRowCol[0], myStartingRowCol[1]).setIsFullyVisible(true);
        Item item = new Item(2);
        getRoom(myEndingRowCol[0], myEndingRowCol[1]).setItem(item);
    }

    /**
     * Gets the starting position of the maze.
     *
     * @return the two ints that make the starting position.
     */
    public int[] getStartingPos() {
        return myStartingRowCol;
    }

    /**
     * Gets the ending position of the maze.
     *
     * @return the two ints that make the ending position.
     */
    public int[] getEndingPos() {
        return myEndingRowCol;
    }

    /**
     * Gets the room from the position.
     * Returns null if the room does not exist.
     *
     * @param theRow the row position.
     * @param theCol the col position.
     * @return the room in the position. If it does not exist, return null.
     */
    public Room getRoom(final int theRow, final int theCol) {
        boolean isValid = false;
        if(theRow < myRooms.length && theRow >= 0) {
            if(theCol < myRooms[0].length && theCol >= 0) {
                isValid = true;
            }
        }
        if(!isValid) {
            System.out.println("Invalid row/col position");
            return null;
        } else {
            return myRooms[theRow][theCol];
        }
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

            if (myRooms[row][col].getIsVisible()) {
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
        myRooms[theRow][theCol].getIsVisible();
        if (!myRooms[theRow][theCol].getIsVisible()) {
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
        // check if the rooms are adjacent and exist
        // in other words, either the row or col can be different by just 1
        // in addition, both must exist.

    }

    /**
     * sets the highlight status of the room.
     *
     * @param theRow the row of the room.
     * @param theCol the col of the room.
     */
    public void setRoomHigLig(final int theRow, final int theCol) {
        this.getRoom(myCurrentRoomHigLig[0], myCurrentRoomHigLig[1]).setHigLig(false);
        this.getRoom(theRow, theCol).setHigLig(true);
        myCurrentRoomHigLig[0] = theRow;
        myCurrentRoomHigLig[1] = theCol;
    }
}
