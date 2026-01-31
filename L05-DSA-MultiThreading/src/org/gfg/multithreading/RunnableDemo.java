package org.gfg.multithreading;

public class RunnableDemo {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Start..");

        MyTask task = new MyTask();

        Thread thread1 = new Thread(task);
        thread1.setName("Worker-01");
        thread1.start();

        Thread thread2 = new Thread(task);
        thread2.setName("Worker-02");
        thread2.start();

        Thread thread3 = new Thread(()->{
            System.out.println("Executing this in:"+Thread.currentThread().getName());
        });
        thread3.setName("Worker-03");
        thread3.start();

        thread1.join();
        thread2.join();
        System.out.println("Done..");
    }
}
