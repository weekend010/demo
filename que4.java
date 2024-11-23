import java.util.*;

public class que4 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the number of elements from the user
        System.out.print("Enter the number of elements: ");
        int n = scanner.nextInt();

        // Get the elements of the array from the user
        int[] array = new int[n];
        System.out.println("Enter the elements: ");
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }

        // Create Best Case (Sorted in ascending order)
        int[] bestCase = Arrays.copyOf(array, array.length);
        Arrays.sort(bestCase); // Sorting array in ascending order for best case

        // Create Worst Case (Sorted in descending order)
        int[] worstCase = Arrays.copyOf(array, array.length);
        Arrays.sort(worstCase);
        reverseArray(worstCase); // Reverse to create worst case

        // Time the Best Case
        long startBest = System.nanoTime();
        mergeSort(bestCase);
        long endBest = System.nanoTime();
        long timeBest = endBest - startBest;

        // Time the Worst Case
        long startWorst = System.nanoTime();
        mergeSort(worstCase);
        long endWorst = System.nanoTime();
        long timeWorst = endWorst - startWorst;

        // Display results
        System.out.println("Best Case MergeSort Time: " + timeBest + " ns");
        System.out.println("Worst Case MergeSort Time: " + timeWorst + " ns");

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

    // Merge Sort implementation
    public static void mergeSort(int[] arr) {
        if (arr.length < 2) {
            return;
        }
        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);

        mergeSort(left);
        mergeSort(right);

        merge(arr, left, right);
    }

    // Merge function to merge two halves
    public static void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }
        while (i < left.length) {
            arr[k++] = left[i++];
        }
        while (j < right.length) {
            arr[k++] = right[j++];
        }
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

// PART B --------------------------------------------------------------------------------
import java.util.*;

public class que4 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the number of cities
        System.out.print("Enter the number of cities: ");
        int n = scanner.nextInt();

        // Create two parent chromosomes (tours)
        List<Integer> parent1 = new ArrayList<>();
        List<Integer> parent2 = new ArrayList<>();

        System.out.println("Enter the cities for Parent 1 chromosome (tour): ");
        for (int i = 0; i < n; i++) {
            parent1.add(scanner.nextInt());
        }

        System.out.println("Enter the cities for Parent 2 chromosome (tour): ");
        for (int i = 0; i < n; i++) {
            parent2.add(scanner.nextInt());
        }

        System.out.println("Parent 1 Chromosome (Tour): " + parent1);
        System.out.println("Parent 2 Chromosome (Tour): " + parent2);

        // Perform crossover
        List<Integer> offspring = crossover(parent1, parent2, n);

        // Show the offspring chromosome (tour)
        System.out.println("Offspring Chromosome (Tour): " + offspring);
    }

    // Order Crossover (OX) function
    public static List<Integer> crossover(List<Integer> parent1, List<Integer> parent2, int n) {
        Random rand = new Random();
        
        // Select two random crossover points
        int start = rand.nextInt(n);
        int end = rand.nextInt(n);
        
        if (start > end) {
            // Swap start and end if necessary to ensure start <= end
            int temp = start;
            start = end;
            end = temp;
        }

        // Create the offspring array initialized with -1 (to indicate unfilled positions)
        List<Integer> offspring = new ArrayList<>(Collections.nCopies(n, -1));

        // Copy the middle part from parent1 to offspring
        for (int i = start; i <= end; i++) {
            offspring.set(i, parent1.get(i));
        }

        // Fill in the remaining positions with genes from parent2
        int index = 0;
        for (int i = 0; i < n; i++) {
            int gene = parent2.get(i);
            // If gene from parent2 is not in the offspring, place it in the next available position
            if (!offspring.contains(gene)) {
                // Find the next available position in the offspring
                while (index < n && offspring.get(index) != -1) {
                    index++;
                }
                if (index < n) {
                    offspring.set(index, gene);
                }
            }
        }

        return offspring;
    }
}
