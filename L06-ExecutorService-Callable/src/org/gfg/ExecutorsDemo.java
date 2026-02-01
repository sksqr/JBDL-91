package org.gfg;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorsDemo {

    public static void main(String[] args) throws InterruptedException {

       // ExecutorService executorService = Executors.newFixedThreadPool(1);
        //ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorService executorService = Executors.newFixedThreadPool(64); // no of worker
        Runnable task = ()-> {
            System.out.println("Starting task... in :"+Thread.currentThread().getName());
            // DB call
            // Network call
            try {
                Thread.sleep(10);// Not Using CPU
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Ending task... in :"+Thread.currentThread().getName());
        };
        long start = System.currentTimeMillis();
        int noOfTask=400;
        for(int i=0; i<noOfTask; i++){
            executorService.submit(task);
        }

        executorService.shutdown(); // non blocking
        executorService.awaitTermination(1, TimeUnit.MINUTES); // blocking
        long end = System.currentTimeMillis();
        System.out.println("Total time Taken: "+(end-start)+" ms");



    }

    private void executorServiceVsThread(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Runnable task = ()-> {
            System.out.println("Starting task... in :"+Thread.currentThread().getName());
            System.out.println("Ending task... in :"+Thread.currentThread().getName());
        };

        Thread thread1 = new Thread(task);
        thread1.start();

        executorService.submit(task);
        executorService.submit(task);
        executorService.submit(task);
        executorService.submit(task);


        System.out.println("Done!");
    }
}
