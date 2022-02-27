import java.util.ArrayList;
import java.util.Arrays;

/**
 * Simple sorting interface for an object that contains a sorting routine.
 * 
 * @author gilbert
 */
public interface ISort {
    /**
     * Sort an array of KeyValuePair objects by key.
     * 
     * @param array KeyValuePair objects to sort
     */
    public void sort(KeyValuePair[] array);
}

class StableQuickSort implements ISort {
    @Override
    public void sort(KeyValuePair[] array) {
        KeyValuePair[] sorted = quickSort(array);
        for (int i = 0; i < sorted.length; i++) {
            array[i] = sorted[i];
        }
    }

    public KeyValuePair[] quickSort(KeyValuePair[] array) {
        int len = array.length;
        if (len <= 1)
            return array;
        KeyValuePair pivot = array[len-1];
        ArrayList<KeyValuePair> small = new ArrayList<KeyValuePair>();
        ArrayList<KeyValuePair> big = new ArrayList<KeyValuePair>();
        for (int i = 0; i < len; i++) {
            if (i < len - 1) {
                KeyValuePair item = array[i];
                if (item.compareTo(pivot) == 1) {
                    big.add(item);
                } else {
                    small.add(item);
                }
            }
        }
        KeyValuePair[] smallArr = new KeyValuePair[small.size()];
        KeyValuePair[] bigArr = new KeyValuePair[big.size()];

        small.toArray(smallArr);
        big.toArray(bigArr);

        KeyValuePair[] sortedSmall = quickSort(smallArr);
        KeyValuePair[] sortedBig = quickSort(bigArr);

        KeyValuePair[] sorted = new KeyValuePair[len];

        for (int i = 0; i < len; i++) {
            if (i < sortedSmall.length) {
                sorted[i] = sortedSmall[i];
            } else if (i == sortedSmall.length) {
                sorted[i] = pivot;
            } else {
                sorted[i] = sortedBig[i - sortedSmall.length - 1];
            }
        }

        return sorted;
    }
}