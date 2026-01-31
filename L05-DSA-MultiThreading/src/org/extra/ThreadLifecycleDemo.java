package org.extra;

public class ThreadLifecycleDemo {

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();

        // 1. NEW: Thread object created but not started
        Thread t1 = new Thread(() -> {
            try {
                // 3. TIMED_WAITING: Triggered by sleep()
                Thread.sleep(200);


                synchronized (lock) {
                    // 4. WAITING: Triggered by wait() without a timeout
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("State after creation: " + t1.getState()); // NEW

        // 2. RUNNABLE: After calling start()
        t1.start();
        System.out.println("State after start(): " + t1.getState()); // RUNNABLE

        // Wait briefly to let t1 enter sleep
        Thread.sleep(100);
        System.out.println("State during sleep(): " + t1.getState()); // TIMED_WAITING

        // 5. BLOCKED: Triggered when trying to enter a locked synchronized block
        synchronized (lock) {
            // Main thread holds the lock; we wait for t1 to finish sleeping and try to enter
            Thread.sleep(300);
            System.out.println("State while t1 waits for lock: " + t1.getState()); // BLOCKED

            // Move t1 from BLOCKED to WAITING by letting it enter and then call wait()
            lock.notify();
        }

        Thread.sleep(100);
        System.out.println("State after lock.wait(): " + t1.getState()); // WAITING

        // Wake it up to finish execution
        synchronized (lock) {
            lock.notify();
        }

        // 6. TERMINATED: After run() completes
        t1.join(); // Main waits for t1 to die
        System.out.println("State after completion: " + t1.getState()); // TERMINATED
    }
}
