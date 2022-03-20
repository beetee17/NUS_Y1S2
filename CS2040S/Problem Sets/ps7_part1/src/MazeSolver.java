import java.util.ArrayList;


public class MazeSolver implements IMazeSolver {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};

	private int startRow;
	private int startCol;
	private Maze maze;
	private boolean[][] visited;

	public MazeSolver() {
		// TODO: Initialize variables.
	}

	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
		this.visited = new boolean[maze.getRows()][maze.getColumns()];
		for (int i = 0; i < maze.getRows(); i++) {
			for (int j = 0; j < maze.getColumns(); j++) {
				this.visited[i][j] = false;
			}
		}
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// set all visited flag to false
		// before we begin our search
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
				maze.getRoom(i, j).onPath = false;
			}
		}
		// used to remember starting point of last search for numReachable
		this.startRow = startRow;
		this.startCol = startCol;

		Room source = this.maze.getRoom(startRow, startCol);
		Room target = this.maze.getRoom(endRow, endCol);

		ArrayList<Room> q = new ArrayList<>();
		q.add(source);

		while(!q.isEmpty()) {
			Room curr = q.remove(0);
			this.visited[curr.x][curr.y] = true;

			if (curr == target) {
				curr.onPath = true;
				int pathLength = 0;
				while (curr.parent != null) {
					curr.onPath = true;
					curr = curr.parent;
					pathLength++;
				}
				return pathLength;
			}

			for (int d = 0; d < 4; d++) {
				if (canGoInDirection(d, curr)) {
					int nextRow = curr.x + DELTAS[d][0];
					int nextCol = curr.y + DELTAS[d][1];
					Room next = this.maze.getRoom(nextRow, nextCol);
					if (!visited[nextRow][nextCol]) {
						q.add(next);
						next.parent = curr;
					}
				}
			}
		}

		return null;
	}

	private boolean canGoInDirection(int d, Room room) {

		switch (d) {
			case NORTH:
				return !room.hasNorthWall();
			case SOUTH:
				return !room.hasSouthWall();
			case EAST:
				return !room.hasEastWall();
			case WEST:
				return !room.hasWestWall();
		}

		return false;
	}

	@Override
	public Integer numReachable(int k) throws Exception {

		for (int i = 0; i < maze.getRows(); ++i)
			for (int j = 0; j < maze.getColumns(); ++j)
				this.visited[i][j] = false;

		Room source = this.maze.getRoom(this.startRow, this.startCol);
		int numTillNextStep = 1;
		int currStep = 0;
		int numForNextStep = 0;

		ArrayList<Room> q = new ArrayList<>();
		q.add(source);

		while(!q.isEmpty()) {
			if (currStep == k) return numTillNextStep;

			Room curr = q.remove(0);
			this.visited[curr.x][curr.y] = true;
			numTillNextStep--;

			for (int d = 0; d < 4; d++) {
				if (canGoInDirection(d, curr)) {
					int nextRow = curr.x + DELTAS[d][0];
					int nextCol = curr.y + DELTAS[d][1];
					Room next = this.maze.getRoom(nextRow, nextCol);
					if (!visited[nextRow][nextCol]) {
						q.add(next);
						numForNextStep++;
					}
				}
			}
			if (numTillNextStep == 0) {
				currStep++;
				numTillNextStep += numForNextStep;
				numForNextStep = 0;
			}
		}
		return 0;
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-empty.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(3, 0, 0, 0));
			ImprovedMazePrinter.printMaze(maze);
//			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
