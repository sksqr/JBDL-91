package org.extra;

public class BusyWaitDemo {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Waiting for 5 sec");
                long startTime = System.currentTimeMillis();
                while(System.currentTimeMillis() - startTime < 5000){
                    ;
                }
                System.out.println("Executing task");
            }
        });


        thread.start();


        System.out.println("Main Completed!");
    }
}
