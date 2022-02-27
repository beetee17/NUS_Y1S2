import java.util.Arrays;

class ZeroOneSorter implements Sorter {
    /**
     * Consider an array consisting of 0s and 1s, what is the most efficient way to sort it? Can you
     * do this in-place? Is it stable? (You should think of the array as containing key/value pairs,
     * where the keys are 0’s and 1’s, but the values are arbitrary.)
     */
    @Override
    public void sort(int[] arr) {
        int start = 0;
        int end = arr.length - 1;

        while (true) {
            while(arr[start] == 0) {
                start++;
            }
            while(arr[end] == 1) {
                end--;
            }
            if (start < end) {
                int temp = arr[start];
                arr[start] = arr[end];
                arr[end] = temp;
            } else { break; }
        }
    }

    public int[] sortNotInPlace(int[] arr) {
        // Not in-place, not stable, O(n) time
        int[] res = new int[arr.length];
        int start = 0;
        int end = res.length - 1;
        for (int i : arr) {
            if (i == 0) {
                res[start] = 0;
                start++;
            } else {
                res[end] = 1;
                end--;
            }
        }
        return res;
    }
}

class BinarySorter implements Sorter {
    /**
     * Consider the following sorting algorithm for sorting integers represent in binary (each specified
     * with the same number of bits): First, use the in-place algorithm from part (a) to sort by the first (high-order) bit.
     * That is, use the high-order bit of each integer as the key to sort by. Once this is done, you have
     * divided the array into two parts: the first part contains all the integers that begin with 0 and the
     * second part contains all the integers that begin with 1. That is, all the elements of the (binary) form
     * ‘0xxxxxxx’ will come before all the elements of the (binary) form ‘1xxxxxxx’.
     * Now, sort the two parts using the same algorithm, but using the second bit instead of the first.
     * And then, sort each of those parts using the 3rd bit, etc.
     */
    @Override
    public void sort(int[] arr) {
        int len = arr.length;;
        String[] binary = new String[len];

        for (int i = 0; i < len; i++) {
            binary[i] = String.format("%64s", Integer.toBinaryString(arr[i])).replace(' ', '0');
        }
        binarySortHelper(binary, 0, len-1, 0);
        int[] tmp = Arrays.stream(binary).mapToInt(x -> Integer.parseInt(x, 2)).toArray();
        for (int i = 0; i < len; i++) {
            arr[i] = tmp[i];
        }
        return;
    }
    public void binarySortHelper(String[] arr, int l, int r, int key) {

        // `arr` contains 64 bit binary representations of integers
        // return pivot index
        if (key == 64 || l > r)
            return;

        int start = l;
        int end = r;

        while (true) {
            int startVal = Integer.parseInt(String.valueOf(arr[start].charAt(key)));
            int endVal = Integer.parseInt(String.valueOf(arr[end].charAt(key)));
            while(startVal == 0) {
                start++;
                if (start > r) {
                    binarySortHelper(arr, l, r, key + 1);
                    return;
                }
                startVal = Integer.parseInt(String.valueOf(arr[start].charAt(key)));
            }
            while(endVal == 1) {
                end--;
                if (end < l) {
                    binarySortHelper(arr, l, r, key + 1);
                    return;
                }
                endVal = Integer.parseInt(String.valueOf(arr[end].charAt(key)));
            }

            if (start < end) {
                String temp = arr[start];
                arr[start] = arr[end];
                arr[end] = temp;

            } else {
                binarySortHelper(arr, l, start-1, key + 1);
                binarySortHelper(arr, start, r, key + 1);
                return;
            }
        }
    }
}

class CountingSorter implements Sorter {

    private int M;

    CountingSorter(int M) {
        this.M = M;
    }

    /**
     * Consider an array consisting of integers between 0 and M, where M is a small integer.
     * For example, imagine an array containing key/value pairs, with all keys in the range {0, 1, 2, 3, 4}.
     * What is the most efficient way to sort it? This time, do not try to do it in-place; you can use
     * extra space to record information about the input array, and you can use an additional array to
     * place the output in.
     * @param arr
     */
    @Override
    public void sort(int[] arr) {
        int[] counts = new int[M+1];
        int len = arr.length;

        Arrays.fill(counts, 0);
        for (int val : arr) {
            counts[val]++;
        }

        // cumulative sum
        for (int i = 1; i <= M; i++) {
            counts[i] += counts[i-1];
        }

        int[] sorted = new int[len];

        // traverse backwards
        for (int j = len - 1; j >= 0; j--) {
            int val = arr[j];
            // index of `val` in the sorted array
            int index = counts[val] - 1;
            sorted[index] = val;
            counts[val]--;
        }
        int[] tmp = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = tmp[i];
        }
        return;
    }
}

public class Q5 {

    public static int[] generateArray(int arraySize, int maxValue) {
        int[] array = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = (int) Math.round(Math.random() * maxValue);
        }
        return array;
    }

    public static void main(String[] args) {
        int NUM_TEST = 10;
        for (int i = 0; i < NUM_TEST; i++) {
            int[] arr = generateArray(20, 20);
            Sorter sorter = new BinarySorter();

            System.out.println("BEFORE: " + Arrays.toString(arr));
            sorter.sort(arr);
            System.out.println("AFTER: " + Arrays.toString(arr) + "\n");
        }
    }
}
