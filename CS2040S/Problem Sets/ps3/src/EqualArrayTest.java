public class EqualArrayTest extends SortSpeedTester {

    EqualArrayTest(ISort sorter, int numTests, int[] arraySizes) {
        super(sorter, numTests, arraySizes);
        System.out.println("Equal Array Speed Test");
    }

    @Override
    public KeyValuePair[] generateArray(int arraySize) {
        KeyValuePair[] array = new KeyValuePair[arraySize];
        for (int j = 0; j < arraySize; j++) {
            array[j] = new KeyValuePair(1, 0);
        }
        return array;
    }
}
