import java.util.Arrays;

public class InsertionSort {

    public void sort(int[] arr) {
        // Recursive Implementation
        int len = arr.length;;
        if (len <= 1)
            return;

        helper(arr, 1);
    }
    public void helper(int[] arr, int index) {
        if (index >= arr.length)
            return;

        int val = arr[index];
        int j = index;

        for (int i = index - 1; i >= 0; i--) {
            if (arr[i] > val) {
                swap(i, j, arr);
                j = i;
            }
        }

        helper(arr, index + 1);
    }

    public void swap(int i, int j, int[] arr) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {3,4,2,1};
        InsertionSort sorter = new InsertionSort();
        sorter.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
