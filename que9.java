import java.util.*;

public class que9 {

    static final int INF = Integer.MAX_VALUE;

    public static class TSPNode {
        List<Integer> path;    // The current path (cities visited)
        int cost;              // The current cost of the path
        int lowerBound;        // The lower bound estimate for this node

        public TSPNode(List<Integer> path, int cost, int lowerBound) {
            this.path = new ArrayList<>(path);
            this.cost = cost;
            this.lowerBound = lowerBound;
        }
    }

    // Function to generate the start (first) node for LCBB
    public static TSPNode generateStartNode(int n, int[][] cost) {
        List<Integer> initialPath = new ArrayList<>();
        initialPath.add(0);  // Start from city 0
        
        // Initially, the cost is 0, and we need to calculate the lower bound for the first node
        int lowerBound = calculateLowerBound(cost, initialPath, n);

        return new TSPNode(initialPath, 0, lowerBound);
    }

    // Function to calculate the lower bound for a given node
    public static int calculateLowerBound(int[][] cost, List<Integer> path, int n) {
        int lowerBound = 0;

        // Add the minimum edge cost from each unvisited city
        for (int i = 0; i < n; i++) {
            if (!path.contains(i)) {
                lowerBound += minEdge(cost, i);
            }
        }

        return lowerBound;
    }

    // Function to find the minimum edge cost from a particular vertex
    public static int minEdge(int[][] cost, int vertex) {
        int min = INF;
        for (int i = 0; i < cost.length; i++) {
            if (cost[vertex][i] != INF && cost[vertex][i] < min) {
                min = cost[vertex][i];
            }
        }
        return min;
    }

    // Function to generate all the children of a given node
    public static List<TSPNode> generateChildren(TSPNode node, int[][] cost, int n) {
        List<TSPNode> children = new ArrayList<>();
        List<Integer> path = node.path;
        int currentCity = path.get(path.size() - 1);  // The last city in the path

        // Try every unvisited city to create the children nodes
        for (int i = 0; i < n; i++) {
            if (!path.contains(i) && cost[currentCity][i] != INF) {
                List<Integer> newPath = new ArrayList<>(path);
                newPath.add(i);  // Add the next city to the path

                int newCost = node.cost + cost[currentCity][i];  // Add the cost to the current city
                int newLowerBound = calculateLowerBound(cost, newPath, n);  // Calculate the lower bound for the child node

                TSPNode childNode = new TSPNode(newPath, newCost, newLowerBound);
                children.add(childNode);
            }
        }

        return children;
    }

    // Function to check if a given node is a leaf node (complete tour)
    public static boolean isLeafNode(TSPNode node, int n, int[][] cost) {
        // A node is a leaf if all cities have been visited
        if (node.path.size() == n) {
            // Check if there's a return edge from the last city to the start city (city 0)
            int lastCity = node.path.get(node.path.size() - 1);
            return cost[lastCity][0] != INF;
        }
        return false;
    }

    // Generate the random cost matrix for cities
    public static int[][] generateCostMatrix(int n) {
        int[][] cost = new int[n][n];
        Random rand = new Random();

        // Fill the cost matrix with random values between 1 and 100 (except diagonal elements)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    cost[i][j] = rand.nextInt(100) + 1;  // Random cost between 1 and 100
                } else {
                    cost[i][j] = INF;  // No path to itself
                }
            }
        }
        return cost;
    }

    // Main method to run the TSP branch and bound algorithm
    public static void main(String[] args) {
        // Number of cities
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of cities: ");
        int n = sc.nextInt();

        // Generate the cost matrix
        int[][] cost = generateCostMatrix(n);
        
        // Print the cost matrix
        System.out.println("Cost Matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cost[i][j] == INF) {
                    System.out.print("INF ");
                } else {
                    System.out.print(cost[i][j] + " ");
                }
            }
            System.out.println();
        }

        // Step 1: Generate the start node
        TSPNode startNode = generateStartNode(n, cost);
        System.out.println("Start Node:");
        System.out.println("Path: " + startNode.path + ", Cost: " + startNode.cost + ", Lower Bound: " + startNode.lowerBound);

        // Step 2: Generate children and check leaf nodes
        List<TSPNode> children = generateChildren(startNode, cost, n);
        System.out.println("\nChildren of the start node:");
        for (TSPNode child : children) {
            System.out.println("Path: " + child.path + ", Cost: " + child.cost + ", Lower Bound: " + child.lowerBound);
        }

        // Step 3: Check if the generated nodes are leaf nodes (complete tours)
        for (TSPNode child : children) {
            if (isLeafNode(child, n, cost)) {
                System.out.println("\nLeaf Node found with complete tour: " + child.path + " with cost: " + child.cost);
            } else {
                System.out.println("\nNot a leaf node: " + child.path);
            }
        }
    }
}
