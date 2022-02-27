import java.util.Collections;
import java.util.PriorityQueue;

public class MedianFinder {

    private PriorityQueue<Integer> small = new PriorityQueue<>(Collections.reverseOrder());
    private PriorityQueue<Integer> large = new PriorityQueue<>();
    private boolean sizeIsEven = true;

    public int getMedian() {
        int median = sizeIsEven ? large.peek() : small.peek();
        remove();
        return median;
    }

    public void insert(int num) {
        if (sizeIsEven) {
            large.offer(num);
            small.offer(large.poll());
        } else {
            small.offer(num);
            large.offer(small.poll());
        }
        sizeIsEven = !sizeIsEven;
    }
    public void remove() {
        if (sizeIsEven) large.poll();
        else small.poll();

        sizeIsEven = !sizeIsEven;
    }

    public static void main(String[] args) {
        MedianFinder medianFinder = new MedianFinder();

        medianFinder.insert(3);
        medianFinder.insert(2);
        medianFinder.insert(4);
        medianFinder.insert(1);

        System.out.println(medianFinder.getMedian());
        System.out.println(medianFinder.getMedian());
        System.out.println(medianFinder.getMedian());
        System.out.println(medianFinder.getMedian());
    }
}
