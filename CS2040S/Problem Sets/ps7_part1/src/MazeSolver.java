import java.util.ArrayList;
import java.util.Arrays;
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
	private boolean solved;
	private boolean[][] visited;
	private Point[][] parent;
	private ArrayList<Integer> numReachables;

	public MazeSolver() {
	}

	private boolean isValid(int row, int col) {
		return !(row < 0 || col < 0 || row >= maze.getRows() || col >= maze.getColumns());
	}

	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
		this.solved = false;
		this.numReachables = new ArrayList<>();
		this.visited = new boolean[maze.getRows()][maze.getColumns()];
		this.parent = new Point[maze.getRows()][maze.getColumns()];
		for (int i = 0; i < maze.getRows(); i++) {
			for (int j = 0; j < maze.getColumns(); j++) {
				this.visited[i][j] = false;
				this.parent[i][j] = null;
				this.maze.getRoom(i, j).onPath = false;
			}
		}
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		if (maze == null)
			throw new Exception("Oh no! You cannot call me without initializing the maze!");

		if (!isValid(startRow, startCol) || !isValid(endRow, endCol))
			throw new IllegalArgumentException("Invalid start/end coordinate");

		// reset the solver
		this.initialize(this.maze);

		int steps = 0;

		Point source = new Point(startRow, startCol);
		Point target = new Point(endRow, endCol);

		ArrayList<Point> q = new ArrayList<>();
		q.add(source);

		while(!q.isEmpty()) {
			ArrayList<Point> nextFrontier = new ArrayList<>();
			this.numReachables.add(0);
			for (Point p : q) {
				if (!this.visited[p.getRow()][p.getCol()]) {
					this.visited[p.getRow()][p.getCol()] = true;
					this.numReachables.set(steps, this.numReachables.get(steps) + 1);
				}

				if (p.equals(target)) this.solved = true;

				nextFrontier.addAll(processCurrent(p));
			}
			q = nextFrontier;
			steps++;
		}
		return this.solved ? solve(target) : null;
	}

	public Integer solve(Point target) {
		int answer = 0;
		this.maze.getRoom(target.row, target.col).onPath = true;
		Point parent = this.parent[target.row][target.col];

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

					Integer dfs = naiveSolver.pathSearch(i, j, endRow, endCol);
					Integer bfs = solver.pathSearch(i, j, endRow, endCol);
					System.out.println("BFS: " + bfs + ", DFS: " + dfs + "\n");
				}
			}

			Integer dfs = naiveSolver.pathSearch(0, 0, 3, 3);
			System.out.println("DFS path length: " + dfs);
			ImprovedMazePrinter.printMaze(maze);
			Integer bfs = solver.pathSearch(0, 0, 3, 3);
			System.out.println("BFS path length: " + bfs);
			ImprovedMazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
