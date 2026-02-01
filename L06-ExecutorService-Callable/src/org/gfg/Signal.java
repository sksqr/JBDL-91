package org.gfg;

public class Signal {

    //private volatile boolean stop;
    private boolean stop;

//    public  boolean isStop() {
//        return stop;
//    }
//
//    public  void setStop(boolean stop) {
//        this.stop = stop;
//    }

    public synchronized boolean isStop() {
        return stop;
    }

    public synchronized void setStop(boolean stop) {
        this.stop = stop;
    }
}
