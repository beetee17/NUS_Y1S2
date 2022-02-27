import java.util.HashMap;

/**
 * This class is a simple example for how to use the sorting classes.
 * It sorts three numbers, and measures how long it takes.
 * STABILITY: B, D, E are unstable sorts
 * E has a similar time complexity on best case vs average case: O(n) ???, likely selection sort
 */
public class SortTestExample {

    public static void checkSort(ISort sorter) {
        int NUM_TEST = 1_000;
        for (int i = 0; i < NUM_TEST; i++) {
            int arraySize = (int) (Math.random() * 10) + 1;
            boolean isSorted = SortingTester.checkSort(sorter, arraySize);
            if (!isSorted) {
                break;
            }
        }
    }

    public static void main(String[] args) {

    }
}
