public class Guessing {

    // Your local variables here
    private int low = 0;
    private int high = 1000;

    /**
     * Implement how your algorithm should make a guess here
     */
    public int guess() {
        if (low >= high) {
            return low;
        }
        return  low + Math.floorDiv(high - low, 2);
    }

    /**
     * Implement how your algorithm should update its guess here
     */
    public void update(int answer) {
        int mid = low + Math.floorDiv(high - low, 2);
        if (answer == 1) {
            // too high
            high = mid - 1;
        } else {
            // too low
            low = mid + 1;
        }
    }
}
