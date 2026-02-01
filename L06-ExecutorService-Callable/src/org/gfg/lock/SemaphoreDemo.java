package org.gfg.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        SemaphoreDemo semaphoreDemo = new SemaphoreDemo();
        long start = System.currentTimeMillis();
        for(int i=0; i<100; i++){
            executorService.submit(()->semaphoreDemo.makeAPICallToFB());
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        long end = System.currentTimeMillis();
        System.out.println("total time:"+(end-start)+"ms");
    }

    private Semaphore semaphore = new Semaphore(2);

    // faceBook API
    //concurrency limit: 10
    public String makeAPICallToFB() throws InterruptedException {
        semaphore.acquire();
        System.out.println("Calling FB:"+Thread.currentThread().getName());
        Thread.sleep(10);
        System.out.println("Done Calling FB:"+Thread.currentThread().getName());
        semaphore.release();
        return "Data";
    }
}
