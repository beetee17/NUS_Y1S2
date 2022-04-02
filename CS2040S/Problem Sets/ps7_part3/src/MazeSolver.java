import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;

public class MazeSolver implements IMazeSolver {
	private static final int TRUE_WALL = Integer.MAX_VALUE;
	private static final int INF = 1_000;
	private static final int EMPTY_SPACE = 0;
	private static final int NORTH = 0, EAST = 1, WEST = 2, SOUTH = 3;
	private static final List<Function<Room, Integer>> WALL_FUNCTIONS = Arrays.asList(
			Room::getNorthWall,
			Room::getEastWall,
			Room::getWestWall,
			Room::getSouthWall
	);

	class Point implements Comparable<Point> {
		private int row;
		private int col;
		private int dist;
		public Point(int row, int col, int dist) {
			this.row = row;
			this.col = col;
			this.dist = dist;
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

		@Override
		public int compareTo(Point o) {
			return this.dist - o.dist;
		}
	}

	private static final int[][] DELTAS = new int[][] {
			{ -1, 0 }, // North
			{ 0, 1 }, // East
			{ 0, -1 }, // West
			{ 1, 0 } // South
	};

	private Maze maze;
	private boolean solved;
	private int[][] distTo;
	private Point[][] parent;
	private PriorityQueue<Point> pq;

	public MazeSolver() {
		// TODO: Initialize variables.
	}

	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
		this.solved = false;
		this.distTo = new int[maze.getRows()][maze.getColumns()];
		this.parent = new Point[maze.getRows()][maze.getColumns()];
		this.pq = new PriorityQueue<>();
		for (int i = 0; i < maze.getRows(); i++) {
			for (int j = 0; j < maze.getColumns(); j++) {
				this.distTo[i][j] = INF;
				this.parent[i][j] = null;
			}
		}
	}

	private boolean isValid(int row, int col) {
		return !(row < 0 || col < 0 || row >= maze.getRows() || col >= maze.getColumns());
	}

	private boolean canGoInDirection(int d, Room room) {
		switch (d) {
			case NORTH:
				return room.getNorthWall() != TRUE_WALL;
			case SOUTH:
				return room.getSouthWall() != TRUE_WALL;
			case EAST:
				return room.getEastWall() != TRUE_WALL;
			case WEST:
				return room.getWestWall() != TRUE_WALL;
		}
		return false;
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		if (maze == null)
			throw new Exception("Oh no! You cannot call me without initializing the maze!");

		if (!isValid(startRow, startCol) || !isValid(endRow, endCol))
			throw new IllegalArgumentException("Invalid start/end coordinate");

		// reset the solver
		this.initialize(this.maze);

		Point source = new Point(startRow, startCol, 0);
		this.pq.add(source);
		this.distTo[startRow][startCol] = 0;

		while (!this.pq.isEmpty()) {
			Point p = this.pq.poll();
			Room r = this.maze.getRoom(p.getRow(), p.getCol());
			for (int d = 0; d < 4; d++) {

				int nextRow = p.row + DELTAS[d][0];
				int nextCol = p.col + DELTAS[d][1];

				if (isValid(nextRow, nextCol) && canGoInDirection(d, r)) {
					int weight = WALL_FUNCTIONS.get(d).apply(r);
					int candidateWeight = this.distTo[p.getRow()][p.getCol()] + (weight == 0 ? 1 : weight);

					Point next = new Point(nextRow, nextCol,  candidateWeight);

					if (this.distTo[nextRow][nextCol] > candidateWeight) {
						this.distTo[nextRow][nextCol] = candidateWeight;
						this.parent[nextRow][nextCol] = p;

						this.pq.add(next);

					}
				}
			}
		}

		return this.distTo[endRow][endCol] == INF ? null : this.distTo[endRow][endCol];
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level given new rules.
		return null;
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol, int sRow, int sCol) throws Exception {
		// TODO: Find minimum fear level given new rules and special room.
		return null;
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("haunted-maze-sample.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 1, 0, 2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
