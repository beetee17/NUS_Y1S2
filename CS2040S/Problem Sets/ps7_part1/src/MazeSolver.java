import java.util.ArrayList;
import java.util.Objects;


public class MazeSolver implements IMazeSolver {
	class Point {
		private int row;
		private int col;
		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}
		public int getRow() {
			return this.row;
		}
		public int getCol() {
			return this.col;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Point point = (Point) o;
			return row == point.row && col == point.col;
		}
	}

	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};

	private Maze maze;
	private boolean[][] visited;
	private Point[][] parent;
	private ArrayList<Integer> numReachables;

	public MazeSolver() {
		// TODO: Initialize variables.
	}

	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
		this.numReachables = new ArrayList<>();
		this.numReachables.add(1);
		this.visited = new boolean[maze.getRows()][maze.getColumns()];
		this.parent = new Point[maze.getRows()][maze.getColumns()];
		for (int i = 0; i < maze.getRows(); i++) {
			for (int j = 0; j < maze.getColumns(); j++) {
				this.visited[i][j] = false;
				this.parent[i][j] = null;
			}
		}
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		if (maze == null)
			throw new Exception("Oh no! You cannot call me without initializing the maze!");


		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns())
			throw new IllegalArgumentException("Invalid start/end coordinate");

		// reset the solver
		this.initialize(this.maze);

		int numTillNextStep = 1;
		int numForNextStep = 0;

		Point source = new Point(startRow, startCol);
		Point target = new Point(endRow, endCol);

		ArrayList<Point> q = new ArrayList<>();
		q.add(source);

		while(!q.isEmpty()) {
			Point p = q.remove(0);
			this.visited[p.getRow()][p.getCol()] = true;
			numTillNextStep--;

			ArrayList<Point> nextFrontier = processCurrent(p);
			q.addAll(nextFrontier);
			numForNextStep += nextFrontier.size();

			if (numTillNextStep == 0) {
				this.numReachables.add(numForNextStep);
				numTillNextStep += numForNextStep;
				numForNextStep = 0;
			}
		}
		return solve(target);
	}

	public Integer solve(Point target) {
		Point parent = this.parent[target.row][target.col];
		if (parent == null) return null;

		int answer = 0;
		this.maze.getRoom(target.row, target.col).onPath = true;
		while (parent != null) {
			answer++;
			this.maze.getRoom(parent.row, parent.col).onPath = true;
			parent = this.parent[parent.row][parent.col];
		}
		return answer;
	}

	private ArrayList<Point> processCurrent(Point p) {
		ArrayList<Point> nextFrontier = new ArrayList<>();
		for (int d = 0; d < 4; d++) {
			if (canGoInDirection(d, this.maze.getRoom(p.getRow(), p.getCol()))) {
				int nextRow = p.row + DELTAS[d][0];
				int nextCol = p.col + DELTAS[d][1];
				Point next = new Point(nextRow, nextCol);
				if (!this.visited[nextRow][nextCol]) {
					nextFrontier.add(next);
					this.parent[nextRow][nextCol] = p;
					this.visited[nextRow][nextCol] = true; // do not double count
				}
			}
		}
		return nextFrontier;
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
			IMazeSolver naiveSolver = new MazeSolverNaive();

			solver.initialize(maze);
			naiveSolver.initialize(maze);

			for (int i = 0; i < maze.getRows(); i ++) {
				for (int j = 0; j < maze.getColumns(); j++) {
					int endRow = (int) (Math.random() * maze.getRows());
					int endCol = (int) (Math.random() * maze.getColumns());
					System.out.println("from row " + i + " to " + endRow);
					System.out.println("from col " + j + " to " + endCol);

					Integer bfs = solver.pathSearch(i, j, endRow, endCol);
					Integer dfs = naiveSolver.pathSearch(i, j, endRow, endCol);
					System.out.println("BFS: " + bfs + ", DFS: " + dfs + "\n");
				}
			}

			ImprovedMazePrinter.printMaze(maze);
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
