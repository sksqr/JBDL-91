package org.gfg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VisitorDemo {

    public static void main(String[] args) throws InterruptedException {


        ExecutorService executorService = Executors.newFixedThreadPool(4);

        VisitorCounterTask visitorCounterTask = new VisitorCounterTask();


        for(int i=0; i<10000; i++){
            executorService.submit(visitorCounterTask);
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("Total Visitor:"+visitorCounterTask.getCount());

    }
}
