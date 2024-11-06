package model;

import controller.QuestionFactory;

import java.util.ArrayList;
import java.util.List;

public class Maze {

    private static final int[] NESW_INVERSE = new int[] {2, 3, 0, 1};
    /** The 2D array of rooms in the maze. */
    private final Room[][] myRooms;

    /** The position of the starting row and starting col. */
    private final int[] myStartingRowCol;

    /** The position of the ending row and ending col. */
    private final int[] myEndingRowCol;

    /** The current room that is highlighted.  */
    private final int[] myCurrentRoomHigLig = new int[] {0, 0};


    /**
     * Creates a maze with the set parameters.
     *
     * @param theRows the number of rows in the maze.
     * @param theColumns the number of columns in the maze.
     * @param theStartingRow the row of the starting room of the maze.
     * @param theStartingCol the column of the starting room of the maze.
     * @param theEndingRow the row of the ending room of the maze.
     * @param theEndingCol the column of the ending room of the maze.
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
        getRoom(myStartingRowCol[0], myStartingRowCol[1]).setVisibility(0);
        Item item = new Item(2);
        getRoom(myEndingRowCol[0], myEndingRowCol[1]).setItem(item);
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
            return null;
        } else {
            return myRooms[theRow][theCol];
        }
    }

    /**
     * Gets whether there are any nearby passages
     * gives in order of NESW
     * If the room does not exist, it will return [-1][-1]
     *
     * @param theRow The row of the room to find the adjacent rooms of.
     * @param theCol The col of the room to find the adjacent rooms of.
     * @return the adjacent rooms.
     */
    public int[][] getAdjacentRooms(final int theRow, final int theCol) {
        int[][] adjacentRooms = new int[4][2];
        int[][] directions = new int[][] {{theRow - 1, theCol},
                {theRow, theCol + 1},
                {theRow + 1, theCol},
                {theRow, theCol - 1},};

        for (int i = 0; i < directions.length; i++) {
            int row = directions[i][0];
            int col = directions[i][1];

            if(getRoom(row, col) != null) {
                adjacentRooms[i] = new int[] {row, col};
            } else {
                adjacentRooms[i] = new int[] {-1, -1};

            }

        }
        return adjacentRooms;
    }

    // major bug that needs to be fixed, the returned boolean is inverted from what it shoul be.
    /**
     * Returns if there is a passage between the two rooms.
     *
     * @param theRow1 the row of the first room.
     * @param theCol1 the column of the first room.
     * @param theRow2 the row of the second room.
     * @param theCol2 the column of the second room.
     * @return whether there is a passage between the rooms.
     */
    private boolean hasPassageBetween(int theRow1, int theCol1, int theRow2, int theCol2) {
        Room room1 = getRoom(theRow1, theCol1);
        Room room2 = getRoom(theRow2, theCol2);

        if(room1 == null || room2 == null) {
            return false;
        }
        int[][] adjacentRooms = getAdjacentRooms(theRow1, theCol1);
        for(int i = 0; i < adjacentRooms.length; i++) {
            Room adjRoom = getRoom(adjacentRooms[i][0], adjacentRooms[i][1]);
            if(adjRoom != null) {
                if(theRow2 == adjacentRooms[i][0] && theCol2 == adjacentRooms[i][1]) {
                    if (room1.getHasNESWDoor(i) && adjRoom.getHasNESWDoor(NESW_INVERSE[i])) {
                        return false;
                    }
                }
            }
        }
        return true;
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

    /**
     * Gets the currently selected room.
     *
     * @return the currently selected room.
     */
    public Room getCurrentlySelectedRoom() {
        return getRoom(myCurrentRoomHigLig[0], myCurrentRoomHigLig[1]);
    }

    /**
     * Updates the visibility of all rooms.
     */
    public void updateRoomVisibility() {
        recursiveRoomVisibilityUpdate(myStartingRowCol[0], myStartingRowCol[1]);
    }

    /**
     * If a room is not visible, make it partiallyvisible.
     * If a room is partially visible, do nothing.
     * If a room is fully visible, update the visibility of adj rooms with a recursive call.
     *
     * @param theRow the row of the room to be checked.
     * @param theCol the col of the room to be checked.
     */
    private void recursiveRoomVisibilityUpdate(int theRow, int theCol) {
        Room room = getRoom(theRow, theCol);
        if(room.getIsFullyVisible()) {
            int[][] theAdjRooms =  getAdjacentRooms(theRow, theCol);
            for(int i = 0; i < theAdjRooms.length; i++) {
                Room adjRoom = getRoom(theAdjRooms[i][0], theAdjRooms[i][1]);
                if(adjRoom != null) {
                    if(hasPassageBetween(theRow, theCol, theAdjRooms[i][0], theAdjRooms[i][1])) {
                        adjRoom.setVisibility(1);
                        if(adjRoom.getIsFullyVisible()) {
                            recursiveRoomVisibilityUpdate(theAdjRooms[i][0], theAdjRooms[i][1]);
                        }
                    }
                }
            }

        } else {
            room.setVisibility(1);
        }
    }


}
