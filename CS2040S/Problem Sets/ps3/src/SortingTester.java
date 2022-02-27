import java.util.Arrays;
import java.util.HashMap;

public class SortingTester {

    public static boolean checkSort(ISort sorter, int size) {

        KeyValuePair[] array = new KeyValuePair[size];
        KeyValuePair[] beforeSort = new KeyValuePair[size];
        for (int i = 0; i < size; i++) {
            int key = (int) (Math.random() * 2*size);
            int value = (int) (Math.random() * 2*size);
            array[i] = new KeyValuePair(key, value);
            beforeSort[i] = new KeyValuePair(key, value);
        }

        sorter.sort(array);

        for (int i = 0; i < size - 1; i++) {
            if (array[i].compareTo(array[i+1]) == 1) {
                System.out.println("NOT SORTED!!!");
                System.out.println("BEFORE: " + Arrays.toString(beforeSort));
                System.out.println("AFTER: " + Arrays.toString(array));
                return false;
            }
        }
        return true;
    }

    public static boolean isStable(ISort sorter, int size) {
        KeyValuePair[] array = new KeyValuePair[size];
        KeyValuePair[] beforeArray = new KeyValuePair[size];
        int count = 0;
        for (int i = 0; i < size; i++) {
            int key = (int) (Math.random() * size/5); // for high chance of duplicates
            array[i] = new KeyValuePair(key, count);
            beforeArray[i] = new KeyValuePair(key, count);
            count += 1;
        }

        sorter.sort(array);

        for (int i = 0; i < size - 1; i++) {
            if (array[i].compareTo(array[i+1]) == 0) {
                // check that values are in sorted order
                if (array[i].getValue() > array[i+1].getValue()) {
                    System.out.println("UNSTABLE");
                    System.out.println("BEFORE: " + Arrays.toString(beforeArray));
                    System.out.println("AFTER: " + Arrays.toString(array));
                    return false;
                }
            }
        }
        return true;
    }
    public static void testStability(HashMap.Entry<String, ISort> item) {
        int NUM_TESTS = 1_000;
        int MAX_ARRAYSIZE = 10;

        String name = item.getKey();
        ISort sorter = item.getValue();
        System.out.println("TESTING SORTER " + name + " STABILITY");
        for (int i = 0; i < NUM_TESTS; i++) {
            int arraySize = (int) (Math.random() * MAX_ARRAYSIZE) + 1;
            boolean stable = SortingTester.isStable(sorter, arraySize);
            if (!stable)
                return;
        }
        System.out.println("PASSED");
    }

    public static void testSort(HashMap.Entry<String, ISort> item) {
        int NUM_TESTS = 1_000;
        int MAX_ARRAYSIZE = 10;

        String name = item.getKey();
        ISort sorter = item.getValue();
        System.out.println("TESTING SORTER " + name + " CORRECTNESS ");
        for (int i = 0; i < NUM_TESTS; i++) {
            int arraySize = (int) (Math.random() * MAX_ARRAYSIZE) + 1;
            boolean correct = SortingTester.checkSort(sorter, arraySize);
            if (!correct)
                return;
        }
        System.out.println("PASSED");
    }

    public static void main(String[] args) {
        HashMap<String, ISort> map = new HashMap<String, ISort>();
//        map.put("A", new SorterA());
//        map.put("B", new SorterB());
//        map.put("C", new SorterC());
//        map.put("D", new SorterD());
//        map.put("E", new SorterE());
//        map.put("F", new SorterF());
        map.put("QUICK", new StableQuickSort());

        SortingTester sortingTester = new SortingTester();

        for (HashMap.Entry<String, ISort> item : map.entrySet()) {
            String name = item.getKey();
            ISort sorter = item.getValue();
            int[] arraySizes = new int[] { 1_000, 2_500, 5_000, 10_000, 100_000 };
            SortSpeedTester speedTester = new RandomArrayTest(sorter, 100, arraySizes);

            System.out.println("TESTING SORTER " + name);

            testStability(item);
            testSort(item);
            speedTester.run();
        }
    }
}
