package org.gfg.lock;

import org.gfg.VisitorCounterTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantDemo {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        VisitorCounterTaskWithLock visitorCounterTask = new VisitorCounterTaskWithLock();


        for(int i=0; i<10000; i++){
            executorService.submit(visitorCounterTask);
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("Total Visitor:"+visitorCounterTask.getCount());

    }


}

class VisitorCounterTaskWithLock implements Runnable{
    private Lock lock = new ReentrantLock();

    private  int count;
    private  void increment(){
        lock.lock();
        count++;
        lock.unlock();
    }

    public int getCount() {
        return count;
    }

    @Override
    public void run() {
        increment();
    }
}
