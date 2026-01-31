package org.gfg.multithreading;

public class MyTask implements Runnable{
    @Override
    public void run() {
        System.out.println("MyTask running in:"+Thread.currentThread().getName());
    }
}
