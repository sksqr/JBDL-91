package org.gfg.multithreading;

public class WaitNotifyDemo {



    public static void main(String[] args) throws InterruptedException {

        Object lock = new Object(); // Monitor or Lock

        Runnable task = ()->{
            System.out.println("Starting..."+Thread.currentThread().getName());
            synchronized (lock){
                System.out.println("Processing data..."+Thread.currentThread().getName());
                try {
                    Thread.sleep(5000); // Thread will in TIMED_WAITING with locks
                    //lock.wait(500);
                    //lock.wait(4000);
                    lock.wait(); // Thread will release lock before going in WAITING state
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Done Processing data..."+Thread.currentThread().getName());
            }
            System.out.println("Completed...."+Thread.currentThread().getName());
        };
        Thread thread1 = new Thread(task);
        thread1.setName("Worker-1");

        Thread thread2 = new Thread(task);
        thread2.setName("Worker-2");

        thread1.start();
        thread2.start();
        Thread.sleep(1000);
        System.out.println(thread1.getState()+":t1");
        System.out.println(thread2.getState()+":t2");
        synchronized (lock){
            System.out.println(thread1.getState());
            //lock.notify();
            lock.notifyAll();
        }
        thread1.join();

        System.out.println("Done...");


    }
}
