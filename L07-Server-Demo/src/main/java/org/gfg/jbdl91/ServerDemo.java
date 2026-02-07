package org.gfg.jbdl91;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerDemo {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Running...");
            System.out.println("Enter request data");
            String data = scanner.nextLine();
            Runnable task = ()-> processRequest(data);
            executorService.submit(task);
        }
    }

    private static void processRequest(String data){
        System.out.println("Processing in "+Thread.currentThread().getName()+ " data : "+data);
        try {
                Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Done!!");
    }
}
