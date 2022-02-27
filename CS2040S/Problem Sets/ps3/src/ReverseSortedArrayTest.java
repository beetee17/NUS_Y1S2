public class ReverseSortedArrayTest extends SortSpeedTester {

    ReverseSortedArrayTest(ISort sorter, int numTests, int[] arraySizes) {
        super(sorter, numTests, arraySizes);
        System.out.println("Reverse Sorted Array Speed Test");
    }

    @Override
    public KeyValuePair[] generateArray(int arraySize) {
        KeyValuePair[] array = new KeyValuePair[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = new KeyValuePair(arraySize - i, 0);
        }
        return array;
    }
}
