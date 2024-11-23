import java.util.*;

public class que10 {

    // Function to perform crossover between two parent chromosomes
    public static List<Integer> crossover(List<Integer> parent1, List<Integer> parent2) {
        int n = parent1.size();
        Random rand = new Random();
        
        // Define the crossover range
        int start = rand.nextInt(n);
        int end = rand.nextInt(n);
        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }

        // Child chromosome
        List<Integer> child = new ArrayList<>(Collections.nCopies(n, -1));

        // Copy a segment from parent1 to the child
        for (int i = start; i <= end; i++) {
            child.set(i, parent1.get(i));
        }

        // Fill the remaining positions with parent2 values in order
        int childIndex = (end + 1) % n;
        for (int i = 0; i < n; i++) {
            int parent2City = parent2.get((end + 1 + i) % n);
            if (!child.contains(parent2City)) {
                child.set(childIndex, parent2City);
                childIndex = (childIndex + 1) % n;
            }
        }

        return child;
    }

    // Function to select a solution (chromosome) from the population using Roulette Wheel Selection
    public static List<Integer> selection(List<List<Integer>> population, List<Integer> fitness) {
        int totalFitness = fitness.stream().mapToInt(Integer::intValue).sum();

        Random rand = new Random();
        int rouletteValue = rand.nextInt(totalFitness);
        
        int cumulativeFitness = 0;
        for (int i = 0; i < fitness.size(); i++) {
            cumulativeFitness += fitness.get(i);
            if (cumulativeFitness > rouletteValue) {
                return population.get(i);
            }
        }

        // Fallback in case no solution is selected (shouldn't happen if fitness is calculated properly)
        return population.get(population.size() - 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input the number of cities
        System.out.print("Enter the number of cities: ");
        int n = sc.nextInt();

        // Input the cost matrix
        int[][] cost = new int[n][n];
        System.out.println("Enter the cost matrix (use INF for no path between cities):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cost[i][j] = sc.nextInt();
            }
        }

        // Generate initial population
        System.out.print("Enter population size: ");
        int populationSize = sc.nextInt();
        List<List<Integer>> population = new ArrayList<>();

        // Generate random permutations for the initial population
        for (int i = 0; i < populationSize; i++) {
            List<Integer> chromosome = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                chromosome.add(j);
            }
            Collections.shuffle(chromosome);
            population.add(chromosome);
        }

        // Display initial population
        System.out.println("\nInitial Population:");
        for (List<Integer> chromosome : population) {
            System.out.println(chromosome);
        }

        // Calculate fitness for each chromosome
        List<Integer> fitness = new ArrayList<>();
        for (List<Integer> chromosome : population) {
            fitness.add(calculateFitness(chromosome, cost));
        }

        // Perform selection and crossover
        System.out.println("\nPerforming selection and crossover...");
        List<Integer> parent1 = selection(population, fitness);
        List<Integer> parent2 = selection(population, fitness);

        System.out.println("Parent 1: " + parent1);
        System.out.println("Parent 2: " + parent2);

        List<Integer> child = crossover(parent1, parent2);

        System.out.println("\nChild generated from crossover: " + child);
        System.out.println("Fitness of child: " + calculateFitness(child, cost));
    }

    // Function to calculate the fitness of a chromosome (lower cost is better)
    public static int calculateFitness(List<Integer> chromosome, int[][] cost) {
        int totalCost = 0;
        for (int i = 0; i < chromosome.size() - 1; i++) {
            totalCost += cost[chromosome.get(i)][chromosome.get(i + 1)];
        }
        // Add the cost to return to the starting city
        totalCost += cost[chromosome.get(chromosome.size() - 1)][chromosome.get(0)];
        return totalCost;
    }
}
