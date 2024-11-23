package model;

/**
 * holds enums used to manage the room class.
 */
public class RoomEnums {

    /**
     * Direction of a door.
     */
    public enum DoorDirection {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }
    /** Holds the DoorDirection values for easy access to their integer equivalents. */
    public static final DoorDirection[] DOOR_DIRECTIONS = DoorDirection.values();

    /**
     * Holds the possible visibility of rooms.
     */
    public enum Visibility {
        VISIBLE,
        LOCKED,
        MYSTERY
    }
    /** Holds the Visibility values for easy access to their integer equivalents. */
    public static final Visibility[] VISIBILITIES = Visibility.values();

    /**
     * The location of each location of room info, to be mapped to a array value.
     */
    public enum RoomArrayValues {
        NORTH_DOOR,
        EAST_DOOR,
        SOUTH_DOOR,
        WEST_DOOR,

        ROOM_FILL,

        ROOM_HIGHLIGHT
    }
    /** Holds the RoomArrayValues values for easy access to their integer equivalents. */
    public static final RoomArrayValues[] ROOM_ARRAY_VALUES = RoomArrayValues.values();

    /**
     * The different possible room information to be mapped to an array.
     */
    public enum RoomInfo {
        // door values
        NORTH_OPEN, // 0
        NORTH_CLOSED, // 1

        EAST_OPEN, // 2
        EAST_CLOSED, // 3

        SOUTH_OPEN, // 4
        SOUTH_CLOSED, // 5

        WEST_OPEN, // 6
        WEST_CLOSED, // 7

        // status values
        LOCKED, // 8
        MYSTERY, // 9

        // extra status
        PERM_LOCKED, // 10
        ENDPOINT_LOCKED, // 11

        // Highlight values
        WITH_HIGHLIGHT, // 12
        NO_HIGHLIGHT, // 13

        // basic values
        LANDSCAPE, // 14
        SANTA, // 14
        TREE, // 16
        FIREPLACE, // 17
        GIFTS, // 18
        MOON // 19
    }
    /** Holds the RoomInfo values for easy access to their integer equivalents. */
    public static final RoomInfo[] ROOM_INFOS = RoomInfo.values();

    /** Holds the value where the fill values begin in the RoomInfos. */
    public static final int ROOM_INFO_FILL_START = 14;

    /**
     * private constructor to prevent instantiation.
     */
    private RoomEnums() {

    }

    /**
     * Gets the inversed door direction of a door direction.
     * @param theNESW the direction the inverse is based off of.
     * @return the inverse of the direction.
     */
    public static DoorDirection inverseDoorDirection(DoorDirection theNESW) {
        return switch (theNESW) {
            case NORTH -> DoorDirection.SOUTH;
            case EAST -> DoorDirection.WEST;
            case SOUTH -> DoorDirection.NORTH;
            case WEST -> DoorDirection.EAST;
        };
    }

}
