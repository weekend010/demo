import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class que11 {

    private final int numPhilosophers;
    private final Fork[] forks;
    private final Philosopher[] philosophers;

    // Constructor initializes the philosophers and forks
    public que11(int numPhilosophers) {
        this.numPhilosophers = numPhilosophers;
        forks = new Fork[numPhilosophers];
        philosophers = new Philosopher[numPhilosophers];
        
        // Initialize forks
        for (int i = 0; i < numPhilosophers; i++) {
            forks[i] = new Fork();
        }
        
        // Initialize philosophers
        for (int i = 0; i < numPhilosophers; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % numPhilosophers]);
            new Thread(philosophers[i]).start();
        }
    }

    // Fork class represents a single fork
    private static class Fork {
        private final Lock lock = new ReentrantLock();
        
        public void pickUp() throws InterruptedException {
            lock.lock();  // Lock the fork
        }

        public void putDown() {
            lock.unlock();  // Unlock the fork
        }
    }

    // Philosopher class represents a philosopher who thinks and eats
    private static class Philosopher implements Runnable {
        private final int id;
        private final Fork leftFork;
        private final Fork rightFork;
        private static final int MAX_EAT_COUNT = 2; // Max iterations to eat
        private static int totalEatCount = 0;      // Total number of eating iterations
        private static final Object lock = new Object(); // Lock to manage totalEatCount
        private int eatCount = 0;                 // Individual philosopher's eat count

        public Philosopher(int id, Fork leftFork, Fork rightFork) {
            this.id = id;
            this.leftFork = leftFork;
            this.rightFork = rightFork;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    think();
                    eat();

                    // Check if we should terminate after two iterations for all philosophers
                    synchronized (lock) {
                        if (totalEatCount >= MAX_EAT_COUNT * 5) {
                            System.exit(0); // Exit the program after all iterations
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Handle interruption
            }
        }

        // Philosopher is thinking for a random time
        private void think() throws InterruptedException {
            System.out.println("Philosopher " + id + " is thinking.");
            Thread.sleep((long) (Math.random() * 1000));  // Simulate thinking time
        }

        // Philosopher is eating for a random time after picking up forks
        private void eat() throws InterruptedException {
            leftFork.pickUp();  // Pick up left fork
            rightFork.pickUp();  // Pick up right fork

            System.out.println("Philosopher " + id + " is eating.");
            Thread.sleep((long) (Math.random() * 1000));  // Simulate eating time

            eatCount++;
            synchronized (lock) {
                totalEatCount++;
            }

            rightFork.putDown();  // Put down right fork
            leftFork.putDown();   // Put down left fork

            System.out.println("Philosopher " + id + " has finished eating " + eatCount + " time(s).");
        }
    }

    public static void main(String[] args) {
        new que11(5); // Start with 5 philosophers
    }
}
