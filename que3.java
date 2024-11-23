import java.util.*;

public class que3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Generate random number of elements between 500 and 600
        int n = 500 + (int) (Math.random() * 101); // Generates a number between 500 and 600
        System.out.println("Number of elements: " + n);

        // Generate random array with n elements
        int[] randomArray = new int[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            randomArray[i] = rand.nextInt(1000); // Random numbers between 0 and 999
        }

        // Create Best Case (Sorted in ascending order)
        int[] bestCase = Arrays.copyOf(randomArray, randomArray.length);
        Arrays.sort(bestCase); // Sorting array in ascending order for best case

        // Create Worst Case (Sorted in descending order)
        int[] worstCase = Arrays.copyOf(randomArray, randomArray.length);
        Arrays.sort(worstCase);
        reverseArray(worstCase); // Reverse to create worst case

        // Time the Best Case
        long startBest = System.nanoTime();
        quicksort(bestCase, 0, bestCase.length - 1);
        long endBest = System.nanoTime();
        long timeBest = endBest - startBest;

        // Time the Worst Case
        long startWorst = System.nanoTime();
        quicksort(worstCase, 0, worstCase.length - 1);
        long endWorst = System.nanoTime();
        long timeWorst = endWorst - startWorst;

        // Display results
        System.out.println("Best Case QuickSort Time: " + timeBest + " ns");
        System.out.println("Worst Case QuickSort Time: " + timeWorst + " ns");

        // Display the first few elements of the sorted array for verification (Best and Worst case)
        System.out.println("Best Case Sorted Elements (First 5): ");
        for (int i = 0; i < 5 && i < bestCase.length; i++) {
            System.out.print(bestCase[i] + " ");
        }
        System.out.println();

        System.out.println("Worst Case Sorted Elements (First 5): ");
        for (int i = 0; i < 5 && i < worstCase.length; i++) {
            System.out.print(worstCase[i] + " ");
        }
        System.out.println();
    }

    // QuickSort implementation
    public static void quicksort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quicksort(arr, low, pivotIndex - 1);
            quicksort(arr, pivotIndex + 1, high);
        }
    }

    // Partition function for QuickSort
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

    // Swap function for QuickSort
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Reverse array to create worst-case scenario
    public static void reverseArray(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
}

//PART B----------------------------------------------------------


import java.util.*;

public class que3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of cities
        System.out.println("Enter the number of cities: ");
        int n = scanner.nextInt();

        // Input city names (or numbers, for simplicity, we'll use 0, 1, 2, ..., n-1)
        System.out.println("Enter the names of the cities (or city numbers 0 to " + (n-1) + "): ");
        int[] cities = new int[n];
        for (int i = 0; i < n; i++) {
            cities[i] = scanner.nextInt();
        }

        // Create initial random tour (chromosome) representing the order of cities
        List<Integer> chromosome = new ArrayList<>();
        for (int city : cities) {
            chromosome.add(city);
        }

        // Show the initial chromosome (tour)
        System.out.println("Initial Chromosome (Tour): " + chromosome);

        // Perform mutation
        System.out.println("Performing Mutation...");
        mutation(chromosome);

        // Show the mutated chromosome (tour)
        System.out.println("Mutated Chromosome (Tour): " + chromosome);
    }

    // Mutation function: Swap Mutation
    public static void mutation(List<Integer> chromosome) {
        Random rand = new Random();

        // Select two random indices for mutation (swap)
        int idx1 = rand.nextInt(chromosome.size());
        int idx2 = rand.nextInt(chromosome.size());

        // Ensure that the indices are different
        while (idx1 == idx2) {
            idx2 = rand.nextInt(chromosome.size());
        }

        // Swap the cities at idx1 and idx2
        Collections.swap(chromosome, idx1, idx2);
    }
}

