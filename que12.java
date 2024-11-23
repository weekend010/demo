public class que12 {

    // Sequential matrix multiplication
    public static int[][] sequentialMultiply(int[][] matA, int[][] matB, int rowsA, int colsA, int colsB) {
        int[][] result = new int[rowsA][colsB];
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += matA[i][k] * matB[k][j];
                }
            }
        }
        return result;
    }

    // Multithreaded matrix multiplication
    public static int[][] multithreadedMultiply(int[][] matA, int[][] matB, int rowsA, int colsA, int colsB) {
        int[][] result = new int[rowsA][colsB];
        Thread[] threads = new Thread[rowsA];

        for (int i = 0; i < rowsA; i++) {
            final int row = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < colsB; j++) {
                    for (int k = 0; k < colsA; k++) {
                        result[row][j] += matA[row][k] * matB[k][j];
                    }
                }
            });
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // Utility function to display a matrix
    public static void displayMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Predefined matrices
        int[][] matA = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        int[][] matB = {
            {9, 8, 7},
            {6, 5, 4},
            {3, 2, 1}
        };

        int rowsA = matA.length;
        int colsA = matA[0].length;
        int colsB = matB[0].length;

        // Display matrices
        System.out.println("Matrix A:");
        displayMatrix(matA);

        System.out.println("\nMatrix B:");
        displayMatrix(matB);

        // Sequential multiplication
        long startTime = System.nanoTime();
        int[][] sequentialResult = sequentialMultiply(matA, matB, rowsA, colsA, colsB);
        long sequentialTime = System.nanoTime() - startTime;

        // Multithreaded multiplication
        startTime = System.nanoTime();
        int[][] multithreadedResult = multithreadedMultiply(matA, matB, rowsA, colsA, colsB);
        long multithreadedTime = System.nanoTime() - startTime;

        // Display results
        System.out.println("\nSequential Multiplication Result:");
        displayMatrix(sequentialResult);

        System.out.println("\nMultithreaded Multiplication Result:");
        displayMatrix(multithreadedResult);

        // Time comparison
        System.out.println("\nTime taken for Sequential Multiplication: " + sequentialTime / 1_000_000.0 + " ms");
        System.out.println("Time taken for Multithreaded Multiplication: " + multithreadedTime / 1_000_000.0 + " ms");
    }
}
