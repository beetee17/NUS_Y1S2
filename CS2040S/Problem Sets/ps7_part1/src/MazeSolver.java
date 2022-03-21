import java.util.ArrayList;


public class MazeSolver implements IMazeSolver {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};

	private Maze maze;
	private boolean[][] visited;
	private ArrayList<Integer> numReachables;
	private boolean solved;

	public MazeSolver() {
		// TODO: Initialize variables.
	}

	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
		this.solved = false;
		this.numReachables = new ArrayList<>();
		this.numReachables.add(1);
		this.visited = new boolean[maze.getRows()][maze.getColumns()];
		for (int i = 0; i < maze.getRows(); i++) {
			for (int j = 0; j < maze.getColumns(); j++) {
				this.visited[i][j] = false;
				this.maze.getRoom(i, j).parent = null;
			}
		}
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// reset the solver
		this.initialize(this.maze);

		int numTillNextStep = 1;
		int numForNextStep = 0;

		Room source = this.maze.getRoom(startRow, startCol);
		Room target = this.maze.getRoom(endRow, endCol);

		ArrayList<Room> q = new ArrayList<>();
		q.add(source);

		while(!q.isEmpty()) {
			Room curr = q.remove(0);
			this.visited[curr.row][curr.col] = true;
			numTillNextStep--;

			if (curr == target) this.solved = true;

			ArrayList<Room> nextRooms = processCurrent(curr);
			q.addAll(nextRooms);
			numForNextStep += nextRooms.size();

			if (numTillNextStep == 0) {
				this.numReachables.add(numForNextStep);
				numTillNextStep += numForNextStep;
				numForNextStep = 0;
			}
		}

		return tracePathFrom(target);
	}
	private ArrayList<Room> processCurrent(Room room) {
		ArrayList<Room> nextRooms = new ArrayList<>();
		for (int d = 0; d < 4; d++) {
			if (canGoInDirection(d, room)) {
				int nextRow = room.row + DELTAS[d][0];
				int nextCol = room.col + DELTAS[d][1];
				Room next = this.maze.getRoom(nextRow, nextCol);
				if (!this.visited[nextRow][nextCol]) {
					nextRooms.add(next);
					if (!solved) next.parent = room;
					this.visited[nextRow][nextCol] = true; // do not double count
				}
			}
		}
		return nextRooms;
	}

	private Integer tracePathFrom(Room target) {
		if (solved) {
			target.onPath = true;
			int pathLength = 0;
			while (target.parent != null) {
				target.onPath = true;
				target = target.parent;
				pathLength++;
			}
			return pathLength;
		} else {
			return null;
		}
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
		if (k >= 0 && k < this.numReachables.size()) return this.numReachables.get(k);
		else return 0;
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-empty.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 3, 3));
//			ImprovedMazePrinter.printMaze(maze);
//			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
