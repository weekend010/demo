public class que5 {

    public static int knapsack(int[] weights, int[] profits, int n, int m) {
        // Create a DP table with dimensions (n+1) x (m+1)
        int[][] dp = new int[n + 1][m + 1];
        
        // Fill the DP table
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= m; w++) {
                // If the current item's weight is less than or equal to the current capacity
                if (weights[i - 1] <= w) {
                    // Take the maximum of including or excluding the current item
                    dp[i][w] = Math.max(profits[i - 1] + dp[i - 1][w - weights[i - 1]], dp[i - 1][w]);
                } else {
                    // If the current item cannot be included, exclude it
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Print the DP Table for debugging
        System.out.println("DP Table:");
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= m; w++) {
                System.out.print(dp[i][w] + " ");
            }
            System.out.println();
        }

        // Now track the solution vector
        boolean[] solution = new boolean[n];
        int w = m;
        
        // Trace back from the bottom-right corner of the DP table to find which items are included
        for (int i = n; i > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {  // Item i is included
                solution[i - 1] = true;
                w -= weights[i - 1];  // Reduce the weight by the weight of the included item
            }
        }

        // Print the solution vector
        System.out.print("Solution vector: ");
        for (boolean itemIncluded : solution) {
            System.out.print(itemIncluded ? "1 " : "0 ");
        }
        System.out.println();

        // Return the maximum profit that can be achieved with the given weight capacity
        return dp[n][m];
    }

    public static void main(String[] args) {
        // Given values
        int[] profits = {10, 10,12,18};  // Profits of the items
        int[] weights = {2,4,6,9};    // Weights of the items
        int n = 4;  // Number of items
        int m = 15;  // Maximum weight capacity

        // Solve the knapsack problem
        int maxProfit = knapsack(weights, profits, n, m);

        // Output the result
        System.out.println("Maximum profit for knapsack with weight capacity " + m + " is: " + maxProfit);
    }
}
