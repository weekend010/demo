import java.util.Scanner;

public class que1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the number of elements
        System.out.print("Enter the number of elements: ");
        int n = scanner.nextInt();

        // Input array elements
        int[] data = new int[n];
        System.out.println("Enter the elements:");
        for (int i = 0; i < n; i++) {
            data[i] = scanner.nextInt();
        }

        // Copy the array for separate sorting
        int[] quicksortData = data.clone();
        int[] mergesortData = data.clone();

        // Perform Quicksort and measure time
        long startQuick = System.nanoTime();
        quicksort(quicksortData, 0, quicksortData.length - 1);
        long endQuick = System.nanoTime();
        long timeQuick = endQuick - startQuick;

        // Perform Mergesort and measure time
        long startMerge = System.nanoTime();
        mergesort(mergesortData, 0, mergesortData.length - 1);
        long endMerge = System.nanoTime();
        long timeMerge = endMerge - startMerge;

        // Display results
        System.out.println("Quicksort Time: " + timeQuick + " ns");
        System.out.println("Mergesort Time: " + timeMerge + " ns");

        // Display sorted elements
        System.out.println("Sorted elements using Quicksort:");
        for (int num : quicksortData) {
            System.out.print(num + " ");
        }
        System.out.println();

        System.out.println("Sorted elements using Mergesort:");
        for (int num : mergesortData) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // Quicksort implementation
    public static void quicksort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quicksort(arr, low, pivotIndex - 1);
            quicksort(arr, pivotIndex + 1, high);
        }
    }

    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Mergesort implementation
    public static void mergesort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergesort(arr, left, mid);
            mergesort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    public static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        System.arraycopy(arr, left, leftArray, 0, n1);
        System.arraycopy(arr, mid + 1, rightArray, 0, n2);

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }
}
