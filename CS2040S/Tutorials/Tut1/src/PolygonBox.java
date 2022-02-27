import java.util.Arrays;

public class PolygonBox {
    public static int[][] naiveBoundingBox(int[][] polygon) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (int[] coordinate : polygon) {
            int xCor = coordinate[0];
            int yCor = coordinate[1];
            minX = Integer.min(minX, xCor);
            minY = Integer.min(minY, yCor);
            maxX = Integer.max(maxX, xCor);
            maxY = Integer.max(maxY, yCor);
        }
        return new int[][] { {minX, minY}, {minX, maxY}, {maxX, maxY}, {maxX, minY} };
    }


    public static int[] fastBoundingBoxHelper(int[] coordinates) {
        // returns { min, max }
        int n = coordinates.length - 1;

        int startGradient = (coordinates[1] - coordinates[0]);
        int endGradient = (coordinates[n] - coordinates[n-1]);
        
        // NOTE: duplicate values _ and ‾ may or may not be present
        // Basic shapes are /\/ and \/\ and /\ and \/
        if ((startGradient > 0 && endGradient > 0) || (startGradient < 0 && endGradient < 0)) {
            // shape is /‾\_/ or \_/‾\
            return new int[] { findTrough(coordinates), findPeak(coordinates) };
        } else {
            // shape is _/\_ or ‾\/‾ or /\/‾ or _/\/ or ...
            // i.e. both ends have gradient 0 or they have different gradients -> changes direction at most once
            return new int[] { searchMin(coordinates), searchMax(coordinates) };
        }
    }

    public static int[][] fastBoundingBox(int[][] polygon) {
        // Assume that polygon is valid -> at least 3 points
        // Base case
        if (polygon.length <= 3)
            return naiveBoundingBox(polygon);

        int[] xCors = Arrays.stream(polygon).mapToInt(point -> point[0]).toArray();
        int[] yCors = Arrays.stream(polygon).mapToInt(point -> point[1]).toArray();

        int[] xs = fastBoundingBoxHelper(xCors);
        int[] ys = fastBoundingBoxHelper(yCors);

        return new int[][] { {xs[0], ys[0]}, {xs[0], ys[1]}, {xs[1], ys[1]}, {xs[1], ys[0]} };
    }

    public static int findPeak(int[] arr) {
        int low = 0;
        int high = arr.length-1;
        while (low <= high) {
            int mid = low + Math.floorDiv(high - low, 2);
            if (arr[mid+1] > arr[mid]) {
                low = mid+1;
            } else if (arr[mid-1] > arr[mid]) {
                   high = mid-1;
            } else {
                // found a peak
                return arr[mid];
            }
        }
        return arr[low];
    }

    public static int findTrough(int[] arr) {
        int low = 0;
        int high = arr.length-1;
        while (low <= high) {
            int mid = low + Math.floorDiv(high - low, 2);
            if (arr[mid+1] < arr[mid]) {
                low = mid+1;
            } else if (arr[mid-1] < arr[mid]) {
                high = mid-1;
            } else {
                // found a trough
                return arr[mid];
            }
        }
        return arr[low];
    }

    public static int searchMin(int dataArray[]) {
        if (dataArray.length == 0)  {
            return Integer.MAX_VALUE; // Maybe throw an Exception here
        }
        int low = 0;
        int high = dataArray.length-1;
        int endpoint = Math.min(dataArray[low], dataArray[high]);

        while (low <= high) {

            int mid = low + Math.floorDiv(high - low, 2);

            // `dataArray` does not change direction, so the min must be at an endpoint
            if (mid <= 0 || mid >= dataArray.length-1)
                return endpoint;

            // We have reached the peak /\ or /‾\  so the min must be at an endpoint
            if (dataArray[mid] >= dataArray[mid + 1] && dataArray[mid] >= dataArray[mid - 1])
                return Math.min(dataArray[0], endpoint);

            // We have reached the trough \/ or \_/ which must be the min
            if (dataArray[mid] <= dataArray[mid + 1] && dataArray[mid] <= dataArray[mid - 1])
                return dataArray[mid];


            // `mid` element is in descending order, search the left half for the pivot
            if (dataArray[mid] > dataArray[mid + 1] && dataArray[mid] < dataArray[mid - 1])
                low = mid + 1;

            else
                high = mid - 1;
        }

        // We should not be reaching here if the input is as specified
        return Integer.MAX_VALUE;
    }

    public static int searchMax(int dataArray[]) {
        if (dataArray.length == 0)  {
            return Integer.MIN_VALUE; // Maybe throw an Exception here
        }
        int low = 0;
        int high = dataArray.length-1;
        int endpoint = Math.max(dataArray[low], dataArray[high]);

        while (low <= high) {

            int mid = low + Math.floorDiv(high - low, 2);

            // `dataArray` does not change direction, so the max must be at an endpoint
            if (mid <= 0 || mid >= dataArray.length-1)
                return endpoint;

            // We have reached the peak /‾\ which must be the max
            if (dataArray[mid] >= dataArray[mid + 1] && dataArray[mid] >= dataArray[mid - 1])
                return dataArray[mid];

            // We have reached the trough \_/ so the max must be at an endpoint
            if (dataArray[mid] <= dataArray[mid + 1] && dataArray[mid] <= dataArray[mid - 1])
                return Math.max(dataArray[0], endpoint);

            // `mid` element is in descending order, search the left half for the pivot
            if (dataArray[mid] > dataArray[mid + 1] && dataArray[mid] < dataArray[mid - 1])
                high = mid - 1;

            else
                low = mid + 1;
        }

        // We should not be reaching here if the input is as specified
        return Integer.MIN_VALUE;
    }

    public static void main(String[] args) {

        int[][][] testCases = new int[][][] {
                { {2,4}, {4,5}, {5,4}, {4,2}, {2,1}, {1,2} },
                { {5,4}, {4,2}, {2,1}, {1,2}, {2,4}, {4,5} },
                { {2,4}, {3,5}, {4, 6}, {5,4}, {4,2}, {2,1}, {1,2} },
                { {5,4}, {4,2}, {2,1}, {1,2}, {2,4}, {3,5}, {4, 6} },
                { {0,0}, {0,1}, {1,1}, {1,0} },
                { {0,1}, {1,1}, {1,0}, {0,0} },
                { {0,0}, {0,1}, {1,1} },
                { {0,0}, {0,1}, {2,2}, {1,1}, {1,0} }

        };

        for (int[][] polygon : testCases) {
            int[][] answer = naiveBoundingBox(polygon);
            int[][] output = fastBoundingBox(polygon);

            if (!Arrays.deepEquals(answer, output)) {
                System.out.println("WRONG");
                System.out.println("Answer: " + Arrays.deepToString(answer));
                System.out.println("Output: " + Arrays.deepToString(output));
                return;
            }
            System.out.println("CORRECT");
        }
    }
}
