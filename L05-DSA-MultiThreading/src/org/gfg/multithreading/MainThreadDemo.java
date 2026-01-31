package org.gfg.multithreading;

public class MainThreadDemo {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Starting...");

        System.out.println(Thread.currentThread().getName());

        MyThread thread = new MyThread();
        thread.setName("My-thread-0");
        //thread.start();
        thread.run();

        thread.join();
        System.out.println("Ending...");
    }
}
