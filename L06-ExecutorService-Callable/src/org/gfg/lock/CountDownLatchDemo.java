package org.gfg.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Future<String>> futureList = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for(int i=0; i<10; i++){
            Callable<String> callable = ()-> {
                String data =": done in "+Thread.currentThread();
                Thread.sleep(100);
                countDownLatch.countDown();
                return data;
            };
            Future<String> stringFuture = executorService.submit(callable);
            futureList.add(stringFuture);
        }

        countDownLatch.await(); //Blocking call
        int i=0;
        for(Future<String> future: futureList){
            if(future.isDone()){
                System.out.println(future.get());
                i++;
            }
        }
        System.out.println("Done!! total:"+i);
    }

}
