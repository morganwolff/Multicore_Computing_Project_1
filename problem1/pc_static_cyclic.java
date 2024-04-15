package problem1;

/**
 * This class calculates the number of prime numbers within a specified range using a cyclic multithreading approach.
 * Each thread checks numbers separated by a fixed block size multiplied by the total number of threads,
 * effectively distributing the workload cyclically among the threads.
 */
public class pc_static_cyclic {
    private static int NUM_END = 200000; // Default upper limit of the range to check for primes
    private static int NUM_THREADS = 4; // Default number of threads to use for computation
    private static int BLOCK_SIZE = 10; // Size of the block each thread processes in one cycle

    /**
     * PrimeCounter extends Thread and calculates the number of prime numbers within a cyclical distribution.
     */
    private static class PrimeCounter extends Thread {
        private int threadIndex; // Index of the thread, used for calculating its starting point
        private int count = 0; // Counter for the number of prime numbers found
        private long executionTime; // Time taken by this thread to complete its task

        /**
         * Constructor for PrimeCounter thread.
         * @param threadIndex the identifier for the thread, starting from 1
         */
        public PrimeCounter(int threadIndex) {
            this.threadIndex = threadIndex;
        }

        /**
         * The main execution method for the thread, which counts primes within its assigned cycle.
         */
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            for (int i = (threadIndex - 1) * BLOCK_SIZE; i < NUM_END; i += NUM_THREADS * BLOCK_SIZE) {
                for (int j = i; j < i + BLOCK_SIZE && j < NUM_END; j++) {
                    if (isPrime(j)) {
                        count++;
                    }
                }
            }
            long endTime = System.currentTimeMillis();
            executionTime = endTime - startTime;
            System.out.println("Thread " + this.threadIndex + " Execution Time: " + executionTime + "ms");
        }

        /**
         * Gets the number of primes found by this thread.
         * @return the count of prime numbers
         */
        public int getCount() {
            return count;
        }
    }

    /**
     * The main method that sets up the threads and starts the prime number calculation.
     * @param args command-line arguments, optionally specifying the number of threads and the range end
     */
    public static void main(String[] args) {
        // Adjusting the number of threads and the range end based on input arguments
        if (args.length == 2) {
            NUM_THREADS = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }
        PrimeCounter[] counters = new PrimeCounter[NUM_THREADS];

        long startTime = System.currentTimeMillis();

        // Initialize and start threads with indices starting from 1
        for (int i = 0; i < NUM_THREADS; i++) {
            counters[i] = new PrimeCounter(i + 1);
            counters[i].start();
        }

        // Collect results from all threads
        int totalPrimes = 0;
        for (PrimeCounter counter : counters) {
            try {
                counter.join();
                totalPrimes += counter.getCount();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        long programExecutionTime = endTime - startTime;

        // Output the results
        System.out.println("Program Execution Time: " + programExecutionTime + "ms");
        System.out.println("1..." + (NUM_END - 1) + " prime# counter=" + totalPrimes);
    }

    /**
     * Utility method to determine if a number is prime.
     * @param x the number to check for primality
     * @return true if the number is a prime, false otherwise
     */
    private static boolean isPrime(int x) {
        if (x <= 1) return false;
        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0) return false;
        }
        return true;
    }
}
