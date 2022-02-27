public class RandomArrayTest extends SortSpeedTester {


    RandomArrayTest(ISort sorter, int numTests, int[] arraySizes) {
        super(sorter, numTests, arraySizes);
        System.out.println("Random Array Speed Test");
    }

    @Override
    public KeyValuePair[] generateArray(int arraySize) {
        KeyValuePair[] array = new KeyValuePair[arraySize];
        for (int i = 0; i < arraySize; i++) {
            int key = (int) (Math.random() * 2 * arraySize);
            int value = (int) (Math.random() * 2 * arraySize);
            array[i] = new KeyValuePair(key, value);
        }
        return array;
    }
}
