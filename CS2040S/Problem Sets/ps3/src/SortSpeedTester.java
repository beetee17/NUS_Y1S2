public abstract class SortSpeedTester {
    private ISort sorter;
    private int numTests;
    private int[] arraySizes;

    SortSpeedTester(ISort sorter, int numTests, int[] arraySizes) {
        this.sorter = sorter;
        this.numTests = numTests;
        this.arraySizes = arraySizes;
    }
    public abstract KeyValuePair[] generateArray(int arraySize);

    public void run() {
        for (int arraySize : this.arraySizes) {
            float timeTaken = this.getAverageTimeFor(arraySize);
            System.out.println("ITEMS: " + arraySize + "   TIME: " + String.format("%.4f", timeTaken) + "ms");
        }
    }

    private float getAverageTimeFor(int arraySize) {
        StopWatch watch = new StopWatch();
        float totalTime = 0;
        for (int i = 0; i < this.numTests; i++) {
            KeyValuePair[] array = generateArray(arraySize);
            watch.start();
            this.sorter.sort(array);
            watch.stop();
            totalTime += watch.getTime();
            watch.reset();
        }
        return totalTime / this.numTests * 100;
    }
}

