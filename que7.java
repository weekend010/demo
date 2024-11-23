import java.util.*;

public class que7 {

    // Number of cities
    static int n;

    // A large number for initialization
    static final int INF = Integer.MAX_VALUE;

    // Store the final minimum cost and the corresponding path
    static int finalRes = INF;
    static List<Integer> bestPath = new ArrayList<>();

    // Function to calculate the minimum edge cost having one end at the vertex
    static int calculateLowerBound(int[][] cost, boolean[] visited, int currPos) {
        int lowerBound = 0;

        // 1. Add the minimum cost edge from the current city (ignoring the visited cities)
        for (int i = 0; i < n; i++) {
            if (!visited[i] && cost[currPos][i] != INF) {
                lowerBound += minEdge(cost, i);
            }
        }

        // 2. Add the minimum cost edge to the current city (ignoring the visited cities)
        for (int i = 0; i < n; i++) {
            if (!visited[i] && cost[i][currPos] != INF) {
                lowerBound += minEdge(cost, i);
            }
        }

        return lowerBound;
    }

    // Function to find the minimum edge cost from a particular vertex
    static int minEdge(int[][] cost, int vertex) {
        int min = INF;
        for (int i = 0; i < n; i++) {
            if (cost[vertex][i] != INF && cost[vertex][i] < min) {
                min = cost[vertex][i];
            }
        }
        return min;
    }

    // Function to implement Branch and Bound for TSP
    static void branchAndBound(int[][] cost, boolean[] visited, int currPos, int count, int costSoFar, List<Integer> currentPath) {
        if (count == n && cost[currPos][0] != INF) {
            // We have visited all cities and returned to the source
            if (costSoFar + cost[currPos][0] < finalRes) {
                finalRes = costSoFar + cost[currPos][0];
                bestPath = new ArrayList<>(currentPath);
                bestPath.add(0); // Add the starting city (0) to complete the tour
            }
            return;
        }

        // Try every unvisited city and recurse
        for (int i = 0; i < n; i++) {
            if (!visited[i] && cost[currPos][i] != INF) {
                // Mark the city as visited
                visited[i] = true;

                // Add this city to the current path
                currentPath.add(i);

                // Calculate the new cost and lower bound
                int newCost = costSoFar + cost[currPos][i];
                int newLowerBound = newCost + calculateLowerBound(cost, visited, i);

                // If the new lower bound is better than the final result, continue the search
                if (newLowerBound < finalRes) {
                    branchAndBound(cost, visited, i, count + 1, newCost, currentPath);
                }

                // Unmark the city as visited and remove from the current path
                visited[i] = false;
                currentPath.remove(currentPath.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        // Read input for number of cities (n)
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of cities: ");
        n = scanner.nextInt();

        // Read the cost matrix (graph) for the cities
        int[][] cost = new int[n][n];
        System.out.println("Enter the cost matrix (adjacency matrix for cities): ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cost[i][j] = scanner.nextInt();
                if (cost[i][j] == 0) {
                    cost[i][j] = INF; // No path between cities, represented by INF
                }
            }
        }

        // Initialize the visited array
        boolean[] visited = new boolean[n];
        Arrays.fill(visited, false);
        visited[0] = true; // Start from city 0

        // List to store the current path
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(0); // Start from city 0

        // Start the Branch and Bound process from city 0
        branchAndBound(cost, visited, 0, 1, 0, currentPath);

        // Print the final result
        System.out.println("The minimum cost of the TSP tour is: " + finalRes);
        System.out.print("The optimal tour path is: ");
        for (int city : bestPath) {
            System.out.print(city + " ");
        }
    }
}
