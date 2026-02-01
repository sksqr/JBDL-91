package org.gfg;

import java.util.concurrent.*;

public class CallableDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

        NetworkCallTask networkCallTask = new NetworkCallTask("request data");
        Thread thread = new Thread(networkCallTask);
        thread.join();
        if(networkCallTask.isResponseAvailable()){
            System.out.println(networkCallTask.getResponse());
        }


        Callable<String> callableTak = () -> {
            System.out.println("Executing callable task in:"+Thread.currentThread().getName());
            Thread.sleep(50);
            return "Task Completed";
        };
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Future<String> stringFuture = executorService.submit(callableTak);
        System.out.println(stringFuture.get(100,TimeUnit.MILLISECONDS)); // blocking
        System.out.println("Done!!");
    }
}
