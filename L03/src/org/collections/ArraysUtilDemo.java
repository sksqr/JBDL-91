package org.collections;

import java.util.Arrays;

public class ArraysUtilDemo {

    public static void main(String[] args) {

        int[] arr = {5,1,7,12,88,10,14};

        printArray(arr);

        Arrays.sort(arr);
        System.out.println("Sorted:");
        printArray(arr);

        int pos = Arrays.binarySearch(arr,12);
        System.out.println("pos:"+pos);


    }

    private static void printArray(int[] array){
        for(int i: array){
            System.out.println(i);
        }
    }
}
