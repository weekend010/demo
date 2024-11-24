import java.util.*;

class demo
{
	
	    static int n = 4; // Number of cities
	    static int[][] cost = {
	        {0, 10, 15, 20},
	        {10, 0, 35, 25},
	        {15, 35, 0, 30},
	        {20, 25, 30, 0}
	    }; // Cost matrix
	    static int minCost = Integer.MAX_VALUE; // Store the minimum cost
	    static List<Integer> bestPath = new ArrayList<>(); // Store the best path

	    // Function to calculate the total cost of a given path
	    static int calculateCost(List<Integer> path) {
	        int totalCost = 0;
	        for (int i = 0; i < path.size() - 1; i++) {
	            totalCost += cost[path.get(i)][path.get(i + 1)];
	        }
	        totalCost += cost[path.get(path.size() - 1)][path.get(0)]; // Return to starting city
	        return totalCost;
	    }

	    // Function to find all permutations of cities and calculate the minimum cost
	    static void tsp(List<Integer> cities, List<Integer> path) {
	        if (path.size() == n) {
	            // Calculate the cost of the current path
	            int currentCost = calculateCost(path);
	            if (currentCost < minCost) {
	                minCost = currentCost;
	                bestPath = new ArrayList<>(path);
	            }
	            return;
	        }

	        // Try all unvisited cities
	        for (int city : cities) {
	            if (!path.contains(city)) {
	                path.add(city);
	                tsp(cities, path);
	                path.remove(path.size() - 1); // Backtrack
	            }
	        }
	    }

	    public static void main(String[] args) {
	        // List of cities (0 to n-1)
	        List<Integer> cities = new ArrayList<>();
	        for (int i = 0; i < n; i++) {
	            cities.add(i);
	        }

	        // Start the TSP with an empty path
	        tsp(cities, new ArrayList<>());

	        // Output the result
	        System.out.println("The minimum cost of the TSP tour is: " + minCost);
	        System.out.println("The optimal tour path is: " + bestPath);
	    }
	

}