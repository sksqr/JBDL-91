package org.gfg.multithreading;

public class WaitingDemo {

    public static void main(String[] args) throws InterruptedException {


//Busy Waiting: Consumes CPU
        Thread thread1 = new Thread(()->{
            System.out.println("Waiting for 3 sec..");
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis()-startTime < 3000){
                ;
            }
            System.out.println("Executing this after 3 sec..");
            System.out.println("Completed Thread");

        });
        thread1.start();
        Thread.sleep(1000);
        System.out.println(thread1.getState());
        thread1.join();


        Thread thread2 = new Thread(()->{
            System.out.println("Waiting for 5 sec..");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Executing this after 5 sec..");
        });

        thread2.start();
        Thread.sleep(1000);
        System.out.println(thread2.getState());
        thread2.join();

        System.out.println("Done..");
    }
}
