import java.util.*;
import java.util.stream.Stream;

public class TSPGraph implements IApproximateTSP {
    private double[][] initialiseAdjacencyMatrix(TSPMap map, int n) {
        double[][] adjMatrix = new double[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                adjMatrix[i][j] = map.pointDistance(i, j);

        return adjMatrix;
    }

    private TreeMapPriorityQueue<Double, Integer> initialisePQ(int n) {
        TreeMapPriorityQueue<Double, Integer> pq = new TreeMapPriorityQueue<>();
        for (int i = 0; i < n; i++) pq.add(i, Double.MAX_VALUE);
        return pq;
    }

    private boolean[] initialiseVisited(int n) {
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) visited[i] = false;
        return visited;
    }

    @Override
    public void MST(TSPMap map) {
        int numPoints = map.getCount();

        double[][] adj = initialiseAdjacencyMatrix(map, numPoints);
        TreeMapPriorityQueue<Double, Integer> pq = initialisePQ(numPoints);

        pq.decreasePriority(0, 0.0);

        boolean[] visited = initialiseVisited(numPoints);
        visited[0] = true;

        HashMap<Integer, Integer> parent = new HashMap<>();
        parent.put(0, null);

        while (!pq.isEmpty()) {
            int curr = pq.extractMin();
            visited[curr] = true;

            for (int nbr = 0; nbr < numPoints; nbr++) {
                double dist = adj[curr][nbr];
                if (dist == 0.0) continue;

                if (!visited[nbr]) {
                    pq.decreasePriority(nbr, dist);
                    if (!parent.containsKey(nbr) || dist < adj[nbr][parent.get(nbr)])
                        parent.put(nbr, curr);
                }
            }
        }

        for (Map.Entry<Integer, Integer> entry : parent.entrySet())
            if (entry.getValue() != null) map.setLink(entry.getKey(), entry.getValue(), false);


        map.redraw();

    }

    @Override
    public void TSP(TSPMap map) {
        MST(map);

        int numPoints = map.getCount();

        int[] parent = new int[numPoints];
        boolean[] visited = initialiseVisited(numPoints);
        HashMap<Integer, ArrayList<Integer>> hashmap = new HashMap<>();

        for (int i = -1 ; i < numPoints; i++) hashmap.put(i, new ArrayList<Integer>());

        for (int i = 0 ; i < numPoints; i++) {
            parent[i] = map.getLink(i);
            Integer key = map.getLink(i);
            hashmap.get(key).add(i);
            map.eraseLink(i, false);
        }

        int curr = 0;
        int temp = -1;

        // Perform depth-first tree walk
        while (curr != 0 || !hashmap.get(0).isEmpty()) {

            if (hashmap.get(curr).isEmpty()) {
                // vertex is a leaf --> backtrack
                int prev = parent[curr];
                if (!visited[curr]) {
                    visited[curr] = true;
                    temp = curr;
                }
                curr = prev;
            } else {
                int next = hashmap.get(curr).remove(0);
                if (!visited[curr]) {
                    visited[curr] = true;
                    map.setLink(curr, next, false);
                } else {
                    map.setLink(temp, next, false);
                }
                curr = next;
            }
        }
        map.setLink(temp, 0, false);

        map.redraw();
    }
    /*
     A valid tour is one that visits every city EXACTLY once, and then returns to the start point.
     */
    @Override
    public boolean isValidTour(TSPMap map) {
        int numPoints = map.getCount();
        boolean[] visited = initialiseVisited(numPoints);

        for (int i = 0; i < numPoints; i++) {
            int nextCityOnTour = map.getLink(i);

            if (nextCityOnTour < 0 || visited[nextCityOnTour]) return false;
            else visited[nextCityOnTour] = true;
        }

        int i = 0;
        int parent = -1;

        while (parent != 0) {
            parent = map.getLink(i == 0 ? 0 : parent);
            i++;
        }

        return i == numPoints;
    }

    @Override
    public double tourDistance(TSPMap map) {
        if (!isValidTour(map)) return -1;

        return Stream.iterate(0, i -> i + 1)
                .limit(map.getCount())
                .map(i -> map.pointDistance(i, map.getLink(i)))
                .reduce(0.0, (x, y) -> x + y);
    }

    public static void main(String[] args) {
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "hundredpoints.txt");
        TSPGraph graph = new TSPGraph();

        // graph.MST(map);
        graph.TSP(map);
        System.out.println(graph.isValidTour(map));
        System.out.println(graph.tourDistance(map));
    }
}
