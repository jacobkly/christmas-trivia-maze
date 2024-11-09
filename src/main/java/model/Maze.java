package model;

import java.util.List;
import java.util.Random;

public class Maze {

    private static final int[] NESW_INVERSE = new int[] {2, 3, 0, 1};
    /** The 2D array of rooms in the maze. */
    private final Room[][] myRooms;

    /** The position of the starting row and starting col. */
    private int[] myStartingRowCol;

    /** The position of the ending row and ending col. */
    private int[] myEndingRowCol;

    /** The current room that is highlighted.  */
    private final int[] myCurrentRoomHigLig = new int[] {0, 0};


    /**
     * Creates a maze with the set parameters.
     *
     * @param theQuestions the questions to be entered into rooms.
     * @param theRows the number of rows in the maze.
     * @param theColumns the number of columns in the maze.
     */
    public Maze(final List<Question> theQuestions, final int theRows, final int theColumns) {

        Room[][] theRooms = new Room[theRows][theColumns];
        for(int i = 0; i < theRows; i++) {
            for(int j = 0; j < theColumns; j++) {
                Question question = theQuestions.removeFirst();
                Room room = new Room(question);
                theRooms[i][j] = room;
            }
        }
        myRooms = theRooms;

        setStartAndEndPosition();
        mazeFirstSetup();
        setRoomHigLig(myStartingRowCol[0], myStartingRowCol[1]);
        mazeBorderCreation();
    }

    /**
     * Sets the starting and ending position of the maze.
     * The start and end positions are random.
     * The end position is set to be some distance from the start position.
     * This should only be called in the constructor.
     */
    private void setStartAndEndPosition() {
        Random random = new Random();
        int randomRow1 = random.nextInt(myRooms.length);
        int randomCol1 = random.nextInt(myRooms[randomRow1].length);
        myStartingRowCol = new int[] {randomRow1, randomCol1};

        // Pick the second random element far away from the first
        // the min distance is manhattan distance of half the row and col lengths.
        int minDistance1 = myRooms.length / 2;
        int minDistance2 = myRooms[0].length / 2; // Define your desired minimum distance
        int minDistance = minDistance1 + minDistance2;

        int randomRow2;
        int randomCol2;
        do {
            randomRow2 = random.nextInt(myRooms.length);
            randomCol2 = random.nextInt(myRooms[randomRow1].length);
        } while (Math.abs(randomRow1 - randomRow2) + Math.abs(randomCol1 - randomCol2) < minDistance);
        myEndingRowCol = new int[] {randomRow2, randomCol2};
        System.out.println("Endpoint is: [" + randomRow2 + ", " + randomCol2 + "]");
    }

    /**
     * Creates the border wall of the maze.
     */
    private void mazeBorderCreation() {
        // top row rooms, north wall
        for(int i = 0; i < myRooms[0].length; i++) {
            myRooms[0][i].setDoor(0, false);
        }
        // bottom row rooms, south wall
        for(int i = 0; i < myRooms[0].length; i++) {
            myRooms[myRooms.length - 1][i].setDoor(2, false);
        }
        // first colomn rooms, west wall
        for(int i = 0; i < myRooms.length; i++) {
            myRooms[i][0].setDoor(3, false);
        }
        // last clumn rooms, east wall
        for(int i = 0; i < myRooms.length; i++) {
            myRooms[i][myRooms[0].length - 1].setDoor(1, false);
        }
    }

    /**
     * Sets the starting and ending rooms to the correct state.
     */
    private void mazeFirstSetup() {
        getRoom(myStartingRowCol[0], myStartingRowCol[1]).setVisibility(0);
        getRoom(myEndingRowCol[0], myEndingRowCol[1]).setAsEndpoint();
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
            return true;
        }
        int[][] adjacentRooms = getAdjacentRooms(theRow1, theCol1);
        for(int i = 0; i < adjacentRooms.length; i++) {
            Room adjRoom = getRoom(adjacentRooms[i][0], adjacentRooms[i][1]);
            if(adjRoom != null) {
                if(theRow2 == adjacentRooms[i][0] && theCol2 == adjacentRooms[i][1]) {
                    if (room1.getHasNESWDoor(i) && adjRoom.getHasNESWDoor(NESW_INVERSE[i])) {
                        return true;
                    }
                }
            }
        }
        return false;
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


    public int getRows() {
        return myRooms.length;
    }

    public int getCols() {
        return myRooms[0].length;
    }
}
