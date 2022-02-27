import java.util.Arrays;

class InversionCounter {
    public static long naiveCountSwaps(int[] arr) {
        long count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    count++;
                }
            }
        }
        return count;
    }
    public static long countSwaps(int[] arr) {
        return mergeSortAndCount(arr, 0, arr.length - 1);
    }

    // Merge sort function
    private static long mergeSortAndCount(int[] arr, int start, int end) {
        if (start < end) {
            int mid = start + Math.floorDiv(end - start, 2);

            // Total inversions = inversions in left subarray + inversions in right subarray + merge inversions
            return mergeSortAndCount(arr, start, mid)
                    + mergeSortAndCount(arr, mid + 1, end)
                    + mergeAndCount(arr, start, mid, mid + 1, end);
        }
        return 0;
    }

    /**
     * Given an input array so that arr[left1] to arr[right1] is sorted and arr[left2] to arr[right2] is sorted
     * (also left2 = right1 + 1), merges the two so that arr[left1] to arr[right2] is sorted, and returns the
     * minimum amount of adjacent swaps needed to do so.
     */
    public static long mergeAndCount(int[] arr, int left1, int right1, int left2, int right2) {
        int[] temp = new int[right2-left1+1];
        int left1Copy = left1;
        int left2Copy = left2;
        long count = 0;

        for (int i = 0; i < temp.length; i++) {
            if (left1 > right1) {
                // left subarray empty
                temp[i] = arr[left2];
                left2++;

            } else if (left2 > right2) {
                // right subarray empty
                temp[i] = arr[left1];
                left1++;
            } else if (arr[left1] <= arr[left2]) {
                // no inversion
                temp[i] = arr[left1];
                left1++;
            } else {
                // inversion(s)
                temp[i] = arr[left2];
                count += (left2Copy-left1);
                left2++;

            }
        }

        for (int i = left1Copy; i <= right2; i++) {
            arr[i] = temp[i-left1Copy];
        }

        return count;
    }

    public static void main(String[] args) {
        int NUM_TEST = 1_000;
        for (int i = 0; i < NUM_TEST; i++) {
            int arraySize = (int) (Math.random() * 10) + 1;
            int[] array = new int[arraySize];

            for (int j = 0; j < arraySize; j++) {
                array[j] = (int) (Math.random() * arraySize);
            }
            long answer = naiveCountSwaps(array);
            long output = countSwaps(array);
            if (answer != output) {
                System.out.println("WRONG");
                System.out.println("ANSWER: " + answer);
                System.out.println("OUTPUT: " + output);
                return;
            }
        }
        System.out.println("CORRECT");
    }
}
