package problem1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class calculates the number of prime numbers using dynamic load balancing
 * where each thread fetches the next number to check from a shared atomic counter.
 */
public class pc_dynamic {
    private static int NUM_END = 200000; // Default upper limit of the number range to check for primes
    private static int NUM_THREADS = 4; // Default number of threads to use for computation
    private static AtomicInteger nextNumber = new AtomicInteger(1); // Atomic counter for the next number to be checked

    /**
     * PrimeCounter extends Thread to dynamically calculate primes by fetching numbers from a shared atomic counter.
     */
    private static class PrimeCounter extends Thread {
        private int count = 0; // Counter for the number of prime numbers found
        private long executionTime; // Time taken by this thread to complete its task
        private int customThreadId; // Custom thread ID for display purposes

        /**
         * Constructor for PrimeCounter thread.
         * @param customThreadId the custom ID for this thread to ensure IDs start at 1
         */
        public PrimeCounter(int customThreadId) {
            this.customThreadId = customThreadId;
        }

        /**
         * The main execution method for the thread, which fetches numbers and checks for primality.
         */
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            while (true) {
                int number = nextNumber.getAndAdd(1);
                if (number >= NUM_END) break;
                if (isPrime(number)) {
                    count++;
                }
            }
            long endTime = System.currentTimeMillis();
            executionTime = endTime - startTime;
            System.out.println("Thread " + this.customThreadId + " Execution Time: " + executionTime + "ms");
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
     * The main method sets up the threads and starts the prime number calculation using a dynamic task assignment.
     * @param args command-line arguments, optionally specifying the number of threads and the range end
     */
    public static void main(String[] args) {
        if (args.length == 2) {
            NUM_THREADS = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }
        PrimeCounter[] counters = new PrimeCounter[NUM_THREADS];

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUM_THREADS; i++) {
            counters[i] = new PrimeCounter(i + 1);
            counters[i].start();
        }

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

        System.out.println("Program Execution Time: " + programExecutionTime + "ms");
        System.out.println("1..." + (NUM_END - 1) + " prime# counter=" + totalPrimes);
    }

    /**
     * Utility method to determine if a number is prime.
     * @param x the number to check for primality
     * @return true if the number is a prime, false otherwise
     */
    private static boolean isPrime(int x) {
        if (x <= 1)
            return false;
        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0)
                return false;
        }
        return true;
    }
}
