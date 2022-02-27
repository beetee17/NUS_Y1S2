import java.util.Arrays;

public class MergeSort {
    public void sort(int[] arr) {
        // start with least partition size of 2^0 = 1
        int width = 1;
        int n = arr.length;
        // subarray size grows by powers of 2
        // since growth of loop condition is exponential,
        // time consumed is logarithmic (log2n)
        while (width < n) {
            // always start from leftmost
            int l = 0;
            while (l < n) {
                int r = Math.min(l + (width * 2 - 1), n - 1);
                int m = Math.floorDiv(l + r, 2);
                // final merge should consider
                // unmerged sublist if input arr
                // size is not power of 2

                if (width > Math.floorDiv(n, 2)) {
                    m = r - (width % n);
                }
                merge(arr, l, m, r);
                l += width * 2;
            }
            // Increasing sub array size by powers of 2
            width *= 2;
        }
    }

    public void merge(int[] arr, int left, int mid, int right) {
        int[] tmp = new int[right-left+1];
        int i = 0;

        int l = left;
        int r = mid + 1;

        while (l <= mid && r <= right) {
            int leftVal = arr[l];
            int rightVal = arr[r];

            if (leftVal < rightVal) {
                tmp[i] = leftVal;
                l++;
            } else {
                tmp[i] = rightVal;
                r++;
            }
            i++;
        }

        while (l <= mid) {
            tmp[i] = arr[l];
            l++;
            i++;
        }
        while (r <= right) {
            tmp[i] = arr[r];
            r++;
            i++;
        }

        for (int j = left; j <= right; j++) {
            arr[j] = tmp[j-left];
        }
    }
    public static void main(String[] args) {
        int[] arr = new int[] {3,4,2,1,5};
        MergeSort sorter = new MergeSort();
        sorter.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
