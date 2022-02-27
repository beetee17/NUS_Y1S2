import java.util.Arrays;

/**
 * Contains static routines for solving the problem of balancing m jobs on p processors
 * with the constraint that each processor can only perform consecutive jobs.
 */
public class LoadBalancing {
    /**
     * Checks if it is possible to assign the specified jobs to the specified number of processors such that no
     * processor's load is higher than the specified query load.
     *
     * @param jobSizes the sizes of the jobs to be performed
     * @param queryLoad the maximum load allowed for any processor
     * @param p the number of processors
     * @return true iff it is possible to assign the jobs to p processors so that no processor has more than queryLoad load.
     */
    public static boolean isFeasibleLoad(int[] jobSizes, int queryLoad, int p) {
        // The time complexity of feasibleLoad is O(n), where n is the size of the array jobSizes
        // Use a greedy approach
        int currentLoad = 0;
        int processorsUsed = 1;

        for (int i=0; i<jobSizes.length; i++) {
            if (jobSizes[i] > queryLoad) {
                // There is no way to not exceed queryLoad
                return false;
            }
            if (currentLoad + jobSizes[i] > queryLoad) {
                // We cannot assign this job to the current processor
                // We need to use a new processor
                currentLoad = jobSizes[i];
                processorsUsed += 1;

                if (processorsUsed > p) {
                    // We have exceeded our quota of processors
                    return false;
                }
            } else {
                // The current processor can take on this job
                currentLoad += jobSizes[i];
            }
        }
        // We have allocated all the jobs to at most p processors
        return true;
    }

    /**
     * Returns the minimum achievable load given the specified jobs and number of processors.
     *
     * @param jobSizes the sizes of the jobs to be performed
     * @param p the number of processors
     * @return the maximum load for a job assignment that minimizes the maximum load
     */
    public static int findLoad(int[] jobSizes, int p) {
        // The time complexity of findLoad is O(n), where n is the size of the array jobSizes
        int begin = 0;
        int end = Integer.MAX_VALUE;

        while (begin < end) {
            int mid = begin + Math.floorDiv(end-begin, 2);
            if (isFeasibleLoad(jobSizes, mid, p)) {
                end = mid;
            } else {
                begin = mid + 1;
            }
        }
        return begin;
    }

    public static int naiveFindLoad(int[] jobSizes, int p) {
        // The time complexity of findLoad is O(n), where n is the size of the array jobSizes
        int i = Arrays.stream(jobSizes).sum();
        while (isFeasibleLoad(jobSizes, i, p)) {
            i = i - 1;
        }
        return i+1;
    }

    // These are some arbitrary testcases.
    public static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, 100, 80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83},
            {7}
    };

    /**
     * Some simple tests for the findLoad routine.
     */
    public static void main(String[] args) {
        int NUM_TESTS = 1000;

        for (int n=0; n<NUM_TESTS; n++) {
            int arraySize = (int) (Math.random() * 99) + 1;
            System.out.println("ARRAY SIZE: " + arraySize);
            int[] jobSizes = new int[arraySize];

            for (int i = 0; i < arraySize; i++) {
                jobSizes[i] = (int) (Math.random() * 100);
            }

            for (int p = 1; p < 30; p++) {
                int answer = naiveFindLoad(jobSizes, p);
                int attempt = findLoad(jobSizes, p);

                if (answer == attempt) {
                    System.out.println("CORRECT");
                } else {
                    System.out.println("WRONG");
                    System.out.println("Processors: " + p);
                    System.out.println(Arrays.toString(jobSizes));
                    System.out.println("ANSWER: " + answer);
                    System.out.println("ATTEMPT: " + attempt);
                    return;
                }
            }
        }
    }
}
