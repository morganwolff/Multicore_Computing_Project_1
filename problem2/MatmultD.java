package problem2;
import java.util.*;
import java.lang.*;

/**
 * The MatmultD class facilitates the multiplication of two matrices using parallel processing with threads.
 */
public class MatmultD {
    // Scanner to read inputs from standard input stream.
    private static Scanner sc = new Scanner(System.in);

    /**
     * Main method which sets up and executes the matrix multiplication using multiple threads.
     * @param args Command line arguments, expects a single integer indicating the number of threads.
     */
    public static void main(String[] args) {
        // Determines the number of threads to use based on command line arguments, default is 1 if no arguments provided.
        int threadCount = args.length == 1 ? Integer.parseInt(args[0]) : 1;

        // Reading two matrices from standard input to be multiplied.
        int[][] a = readMatrix();
        int[][] b = readMatrix();

        // Dimensions for matrix multiplication.
        int m = a.length; // Number of rows in the first matrix.
        int p = b[0].length; // Number of columns in the second matrix.

        // Result matrix initialized.
        int[][] c = new int[m][p];

        // Timing the parallel execution start.
        long startTime = System.currentTimeMillis();
        Thread[] threads = new Thread[threadCount];
        long[] threadTimes = new long[threadCount]; // To store execution time for each thread.
        Object lock = new Object();  // A lock object for synchronization

        // Distributing rows to threads based on the static load balancing method.
        int rowsPerThread = m / threadCount;
        int extraRows = m % threadCount;

        int startRow = 0;
        for (int i = 0; i < threadCount; i++) {
            int rows = i < extraRows ? rowsPerThread + 1 : rowsPerThread; // Handling any uneven distribution of rows.
            int endRow = startRow + rows;

            // Creating and starting threads for matrix multiplication.
            threads[i] = new Thread(new MatrixMultiplier(a, b, c, startRow, endRow, threadTimes, i, lock));
            threads[i].start();
            startRow = endRow; // Updating the start row for the next thread.
        }

        // Waiting for all threads to complete their tasks.
        try {
            for (int i = 0; i < threadCount; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Timing the parallel execution end.
        long endTime = System.currentTimeMillis();

        // Outputting the result matrix and performance data.
        printMatrix(c);
        System.out.println("[Total Time]:" + (endTime - startTime) + " ms");
        for (int i = 0; i < threadCount; i++) {
            System.out.println("[Thread " + i + " Time]:" + threadTimes[i] + " ms");
        }
    }

    /**
     * Reads a matrix from standard input.
     * @return The matrix read from standard input.
     */
    public static int[][] readMatrix() {
        int rows = sc.nextInt(); // Number of rows.
        int cols = sc.nextInt(); // Number of columns.
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = sc.nextInt(); // Reading each element.
            }
        }
        return result;
    }

    /**
     * Prints the given matrix and calculates the sum of all its elements.
     * @param mat The matrix to print and sum.
     */
    public static void printMatrix(int[][] mat) {
        System.out.println("Matrix[" + mat.length + "][" + mat[0].length + "]");
        int sum = 0;
        for (int[] row : mat) {
            for (int value : row) {
                sum += value;
            }
        }
        System.out.println("Matrix Sum = " + sum + "\n");
    }
}

/**
 * Runnable implementation for matrix multiplication by a thread.
 * Handles multiplication for a subset of rows from the first matrix.
 */
class MatrixMultiplier implements Runnable {
    private int[][] a; // First matrix.
    private int[][] b; // Second matrix.
    private int[][] c; // Result matrix.
    private int startRow; // Starting row index for this thread's computation.
    private int endRow; // Ending row index for this thread's computation.
    private long[] threadTimes; // Array to store execution time for each thread.
    private int threadIndex; // Index of the current thread.
    private final Object lock;  // Lock object for synchronization

    MatrixMultiplier(int[][] a, int[][] b, int[][] c, int startRow, int endRow, long[] threadTimes, int threadIndex, Object lock) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.startRow = startRow;
        this.endRow = endRow;
        this.threadTimes = threadTimes;
        this.threadIndex = threadIndex;
        this.lock = lock;
    }

    /**
     * The run method executed by each thread, performs matrix multiplication for assigned rows.
     */
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < b[0].length; j++) {
                int sum = 0;
                for (int k = 0; k < b.length; k++) {
                    sum += a[i][k] * b[k][j];
                }
                synchronized (lock) {
                    c[i][j] += sum;
                }
            }
        }
        long endTime = System.currentTimeMillis();
        threadTimes[threadIndex] = endTime - startTime; // Recording the execution time for this thread.
    }
}
