package org.collections;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class QueueDemo {

    public static void main(String[] args) {

//        Queue<Integer> queue = new LinkedList<>();
//        queue.offer(11);
//        queue.offer(12);
//        queue.offer(13);
//        queue.offer(14);
//        System.out.println(queue.poll());
//        System.out.println(queue.poll());
//        System.out.println(queue.peek());
//        System.out.println(queue.peek());


        System.out.println("Min HEap");
        Queue<Integer> pQueue = new PriorityQueue<>(); // minHeap
        pQueue.offer(11);
        pQueue.offer(12);
        pQueue.offer(13);
        pQueue.offer(7);
        pQueue.offer(14);
        pQueue.offer(10);
        System.out.println(pQueue.poll());
        System.out.println(pQueue.poll());
        System.out.println(pQueue.peek());
        System.out.println(pQueue.peek());


        System.out.println("MAx HEap");
        pQueue = new PriorityQueue<>(Comparator.reverseOrder()); // maxHeap
        pQueue.offer(11);
        pQueue.offer(12);
        pQueue.offer(13);
        pQueue.offer(7);
        pQueue.offer(14);
        pQueue.offer(10);
        System.out.println(pQueue.poll());
        System.out.println(pQueue.poll());
        System.out.println(pQueue.peek());
        System.out.println(pQueue.peek());

    }
}
