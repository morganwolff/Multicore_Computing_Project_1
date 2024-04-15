package problem1;
public class pc_static_cyclic {
    private static int NUM_END = 200000; // default number value
    private static int NUM_THREADS = 4; // default thread number value
    private static int BLOCK_SIZE = 10;

    private static class PrimeCounter extends Thread {
        private int threadIndex;
        private int count = 0;
        private long executionTime;

        public PrimeCounter(int threadIndex) {
            this.threadIndex = threadIndex;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            for (int i = threadIndex * BLOCK_SIZE; i < NUM_END; i += NUM_THREADS * BLOCK_SIZE) {
                for (int j = i; j < i + BLOCK_SIZE && j < NUM_END; j++) {
                    if (isPrime(j)) {
                        count++;
                    }
                }
            }
            long endTime = System.currentTimeMillis();
            executionTime = endTime - startTime;
            System.out.println("Thread " + this.threadId() + " Execution Time: " + executionTime + "ms");
        }

        public int getCount() {
            return count;
        }

        public long getExecutionTime() {
            return executionTime;
        }
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            NUM_THREADS = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }
        PrimeCounter[] counters = new PrimeCounter[NUM_THREADS];

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUM_THREADS; i++) {
            counters[i] = new PrimeCounter(i);
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
