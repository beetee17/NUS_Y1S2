import java.util.Arrays;

/**
 * The Optimization class contains a static routine to find the maximum in an array that changes direction at most once.
 */
public class Optimization {

    /**
     * A set of test cases.
     */
    static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, -100, -80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83},
            {1, 2, 3, 4, 5},
            {-1, -2, -3, -4, -5},
            {1},
            {}
    };

    /**
     * Returns the maximum item in the specified array of integers which changes direction at most once.
     *
     * @param dataArray an array of integers which changes direction at most once.
     * @return the maximum item in data Array
     */
    public static int searchMax(int dataArray[]) {
        if (dataArray.length == 0)  {
            return Integer.MIN_VALUE; // Maybe throw an Exception here
        }
        int low = 0;
        int high = dataArray.length-1;
        int endpoint = Math.max(dataArray[low], dataArray[high]);

        while (low <= high) {

            int mid = low + Math.floorDiv(high - low, 2);
            // There are two elements left to check
            if ((high - low == 1)) {
                int maxOfLowAndHigh = Math.max(dataArray[low], dataArray[high]);
                return Math.max(maxOfLowAndHigh, endpoint);
            }
            // `dataArray` does not change direction, so the max must be at an endpoint
            if (mid <= 0 || mid >= dataArray.length-1)
                return endpoint;

            // We have reached the peak /\ which must be the max
            if (dataArray[mid] > dataArray[mid + 1] && dataArray[mid] > dataArray[mid - 1])
                return dataArray[mid];

            // We have reached the trough \/ so the max must be at an endpoint
            if (dataArray[mid] < dataArray[mid + 1] && dataArray[mid] < dataArray[mid - 1])
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

    /**
     * A routine to test the searchMax routine.
     */
    public static void main(String[] args) {
        for (int[] testCase : testCases) {
            System.out.println(searchMax(testCase));
        }
        int NUM_TESTS = 1;

        for (int n=0; n<NUM_TESTS; n++) {
            int arraySize = 3;
            int[] arr = new int[arraySize];

            Arrays.fill(arr, Integer.MAX_VALUE);
            int currMax = Integer.MIN_VALUE;

            int indexOfLeftSortedSubArrayEnd = (int) (Math.random() * (arraySize-1));

            for (int j=0; j<indexOfLeftSortedSubArrayEnd; j++) {
                // We should check for duplicates instead of leaving it to probability
                arr[j] = (int) (Math.random() * 100000);
                currMax = Math.max(currMax, arr[j]);

            }

            Arrays.sort(arr);

            for (int i=indexOfLeftSortedSubArrayEnd; i<arraySize; i++) {
                arr[i] = (int) (Math.random() * currMax);
                currMax = arr[i];
            }

            int answer = Arrays.stream(arr).max().getAsInt();
            int output = searchMax(arr);

            System.out.println(Arrays.toString(arr));

            if (answer == output) {
                System.out.println("CORRECT");
                System.out.println("Answer was " + answer);
                System.out.println("Output was " + output);
            } else {
                System.out.println("WRONG");
                System.out.println("Answer was " + answer);
                System.out.println("Output was " + output);
                return;
            }

        }
    }
}
