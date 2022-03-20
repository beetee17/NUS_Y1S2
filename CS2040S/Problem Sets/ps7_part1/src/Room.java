/**
 * Represents a single room in the maze.
 */
public class Room {
	private boolean westWall, eastWall, northWall, southWall;
	public boolean onPath;
	public int x;
	public int y;
	public Room parent;

	Room(int x, int y, boolean north, boolean south, boolean east, boolean west) {
		this.x = x;
		this.y = y;
		this.northWall = north;
		this.southWall = south;
		this.eastWall = east;
		this.westWall = west;

		this.onPath = false;
	}

	/**
	 * @return true iff there is a wall to the west of the room
	 */
	public boolean hasWestWall() {
		return westWall;
	}

	/**
	 * @return true iff there is a wall to the east of the room
	 */
	public boolean hasEastWall() {
		return eastWall;
	}

	/**
	 * @return true iff there is a wall to the north of the room
	 */
	public boolean hasNorthWall() {
		return northWall;
	}

	/**
	 * @return true iff there is a wall to the south of the room
	 */
	public boolean hasSouthWall() {
		return southWall;
	}
}
