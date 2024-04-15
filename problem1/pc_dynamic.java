package problem1;
import java.util.concurrent.atomic.AtomicInteger;

public class pc_dynamic {
    private static int NUM_END = 200000; // default number value
    private static int NUM_THREADS = 4; // default thread number value
    private static AtomicInteger nextNumber = new AtomicInteger(1);

    private static class PrimeCounter extends Thread {
        private int count = 0;
        private long executionTime;

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
            counters[i] = new PrimeCounter();
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
