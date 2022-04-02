import java.util.ArrayList;

public class MazeSolverWithPower implements IMazeSolverWithPower {

	class Point {
		private int row;
		private int col;
		private int superpowers;

		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public Point(int row, int col, int superpowers) {
			this.row = row;
			this.col = col;
			this.superpowers = superpowers;
		}

		public int getRow() {
			return this.row;
		}
		public int getCol() {
			return this.col;
		}
		public int getSuperpowers() { return this.superpowers; }

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
	private Point[][][] parent;
	private boolean[][] visited;
	private boolean[][][] superVisited;
	private ArrayList<Integer> numReachables;

	public MazeSolverWithPower() {
		// TODO: Initialize variables.
	}

	@Override
	public void initialize(Maze maze) {
		initialize(maze, 0);
	}

	public void initialize(Maze maze, int superpowers) {
		this.maze = maze;
		this.solved = false;
		this.numReachables = new ArrayList<>();
		this.visited = new boolean[maze.getRows()][maze.getColumns()];
		this.superVisited = new boolean[maze.getRows()][maze.getColumns()][superpowers + 1];
		this.parent = new Point[maze.getRows()][maze.getColumns()][superpowers + 1];
		for (int i = 0; i < maze.getRows(); i++) {
			for (int j = 0; j < maze.getColumns(); j++) {
				this.visited[i][j] = false;
				this.maze.getRoom(i, j).onPath = false;
				for (int k = 0; k <= superpowers; k++) {
					this.superVisited[i][j][k] = false;
					this.parent[i][j][k] = null;
				}
			}
		}
	}


	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		return pathSearch(startRow, startCol, endRow, endCol, 0);
	}

	private boolean isValid(int row, int col) {
		return  !(row < 0 || col < 0 || row >= maze.getRows() || col >= maze.getColumns());
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol, int superpowers) throws Exception {
		if (maze == null)
			throw new Exception("Oh no! You cannot call me without initializing the maze!");

		if (!isValid(startRow, startCol) || !isValid(endRow, endCol))
			throw new IllegalArgumentException("Invalid start/end coordinate");

		// reset the solver
		this.initialize(this.maze, superpowers);

		int steps = 0;

		Point source = new Point(startRow, startCol, superpowers);
		Point target = new Point(endRow, endCol, superpowers);

		ArrayList<Point> q = new ArrayList<>();
		q.add(source);

		while(!q.isEmpty()) {
			ArrayList<Point> nextFrontier = new ArrayList<>();
			this.numReachables.add(0);
			for (Point p : q) {
				int row = p.getRow();
				int col = p.getCol();
				this.superVisited[row][col][p.superpowers] = true;

				if (!this.visited[row][col]) {
					this.visited[row][col] = true;
					this.numReachables.set(steps, this.numReachables.get(steps) + 1);
				}

				if (!this.solved && p.equals(target)) {
					this.solved = true;
					target = p;
				}

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
		Point parent = this.parent[target.row][target.col][target.superpowers];

		while (parent != null) {
//			System.out.println("row: " + parent.row + ", col: " + parent.col);
			answer++;
			this.maze.getRoom(parent.row, parent.col).onPath = true;
			parent = this.parent[parent.row][parent.col][parent.superpowers];
		}
		return answer;
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

	private ArrayList<Point> processCurrent(Point p) {
		ArrayList<Point> nextFrontier = new ArrayList<>();
		for (int d = 0; d < 4; d++) {
			Room neighbour = this.maze.getRoom(p.getRow(), p.getCol());
			if (canGoInDirection(d, neighbour)) {
				int nextRow = p.row + DELTAS[d][0];
				int nextCol = p.col + DELTAS[d][1];
				Point next = new Point(nextRow, nextCol, p.superpowers);
				if (!this.superVisited[nextRow][nextCol][p.superpowers]) {
					nextFrontier.add(next);
					this.parent[nextRow][nextCol][p.superpowers] = p;
					this.superVisited[nextRow][nextCol][p.superpowers] = true; // do not double count
				}
			} else if (canBreakWallInDirection(d, neighbour, p.superpowers)) {
				int nextRow = p.row + DELTAS[d][0];
				int nextCol = p.col + DELTAS[d][1];
				int nextPowers = p.superpowers - 1;
				Point next = new Point(nextRow, nextCol, nextPowers);
				if (isValid(nextRow, nextCol) && !this.superVisited[nextRow][nextCol][nextPowers]) {
					nextFrontier.add(next);
					this.parent[nextRow][nextCol][nextPowers] = p;
					this.superVisited[nextRow][nextCol][nextPowers] = true; // do not double count
				}
			}
		}
		return nextFrontier;
	}

	private boolean canBreakWallInDirection(int d, Room room, int superpowers) {
		if (superpowers <= 0) return false;

		switch (d) {
			case NORTH:
				return room.hasNorthWall();
			case SOUTH:
				return room.hasSouthWall();
			case EAST:
				return room.hasEastWall();
			case WEST:
				return room.hasWestWall();
		}

		return false;
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		if (k >= 0 && k < this.numReachables.size()) return this.numReachables.get(k);
		else return 0;
	}

	public static void main(String[] args) {
//		try {
//			Maze maze = Maze.readMaze("maze-empty.txt");
//			IMazeSolver solver = new MazeSolverWithPower();
//
//			solver.initialize(maze);
//
//			for (int i = 0; i < maze.getRows(); i ++) {
//				for (int j = 0; j < maze.getColumns(); j++) {
//					int endRow = (int) (Math.random() * maze.getRows());
//					int endCol = (int) (Math.random() * maze.getColumns());
//					System.out.println("from row " + i + " to " + endRow);
//					System.out.println("from col " + j + " to " + endCol);
//
//					Integer bfs = solver.pathSearch(i, j, endRow, endCol);
//					System.out.println("BFS: " + bfs);
//				}
//			}
//
//			ImprovedMazePrinter.printMaze(maze);
//			MazePrinter.printMaze(maze);
//
//			for (int i = 0; i <= 9; ++i) {
//				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		try {
			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolverWithPower solver = new MazeSolverWithPower();
			solver.initialize(maze);

//			System.out.println(solver.pathSearch(0, 0, 0, 0, 2));
			System.out.println(solver.pathSearch(0, 0, 3, 3));
			ImprovedMazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
