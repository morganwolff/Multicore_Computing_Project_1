package problem1;

/**
 * This class calculates the number of prime numbers within a specified range using multithreading.
 * It distributes the range across multiple threads to parallelize the computation.
 */
public class pc_static_block {
    private static int NUM_END = 200000; // Default upper limit of the number range to check for primes
    private static int NUM_THREADS = 4; // Default number of threads to use for computation

    /**
     * PrimeCounter extends Thread to calculate the number of prime numbers within a specific range.
     */
    private static class PrimeCounter extends Thread {
        private int start; // Start of the range this thread will process
        private int end; // End of the range this thread will process
        private int count = 0; // Counter for the number of prime numbers found
        private long executionTime; // Time taken by this thread to complete its task
        private int threadId; // Custom ID for easier tracking of threads during output
        
        /**
         * Constructor for PrimeCounter thread.
         * @param start the beginning index of the range to check for primes
         * @param end the ending index of the range to check for primes
         * @param threadId the identifier for the thread
         */
        public PrimeCounter(int start, int end, int threadId) {
            this.start = start;
            this.end = end;
            this.threadId = threadId;
        }

        /**
         * The main execution method for the thread, which counts primes within its assigned range.
         */
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            for (int i = start; i < end; i++) {
                if (isPrime(i)) {
                    count++;
                }
            }
            long endTime = System.currentTimeMillis();
            executionTime = endTime - startTime;
            System.out.println("Thread " + this.threadId + " Execution Time: " + executionTime + "ms");
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
        int range = NUM_END / NUM_THREADS;

        long startTime = System.currentTimeMillis();

        // Initialize and start threads
        for (int i = 0; i < NUM_THREADS; i++) {
            int start = i * range;
            int end = (i + 1) * range;
            if (i == NUM_THREADS - 1) {
                end = NUM_END;  // Ensure the last thread covers the remaining range
            }
            counters[i] = new PrimeCounter(start, end, i + 1);
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
