package org.gfg;

import java.util.concurrent.atomic.AtomicInteger;

public class VisitorCounterTask implements Runnable{

//    private int count = 0;

    private AtomicInteger count = new AtomicInteger(0);
    @Override
    public void run() {
        increment();
    }

//    private synchronized void increment(){ // lock on this object
//        count++;
//    }

    private  void increment(){
        count.incrementAndGet(); // Atomic Operation.
    }

    public int getCount() {
        return count.get();
    }
}
