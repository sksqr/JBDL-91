package org.gfg;

public class MemoryIssueDemo {

    public static void main(String[] args) throws InterruptedException {

        Signal signal = new Signal();

        Thread thread1 = new Thread(() -> {
            try {
                /*

                 */
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            signal.setStop(true);
            System.out.println("Setting to true in thread0 " + Thread.currentThread().getName());
        });

        Thread thread2 = new Thread(() -> {
            while (!signal.isStop()) {
                System.out.println("Executing in thread1 " + Thread.currentThread().getName());
            }
        });

        thread2.start();
        thread1.start();


        thread1.join();
        thread2.join();

        System.out.println("Done!");
    }
}
