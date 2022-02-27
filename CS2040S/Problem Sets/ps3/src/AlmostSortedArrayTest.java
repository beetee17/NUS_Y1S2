public class AlmostSortedArrayTest extends SortSpeedTester {

    AlmostSortedArrayTest(ISort sorter, int numTests, int[] arraySizes) {
        super(sorter, numTests, arraySizes);
        System.out.println("Almost Sorted Array Speed Test");
    }

    @Override
    public KeyValuePair[] generateArray(int arraySize) {
        KeyValuePair[] array = new KeyValuePair[arraySize];
        for (int j = 0; j < arraySize - 1; j++) {
            array[j] = new KeyValuePair(j + 1, 0);
        }
        array[arraySize - 1] = new KeyValuePair(0, 0);
        return array;
    }
}
