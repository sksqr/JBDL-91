package org.collections;

import java.util.ArrayList;
import java.util.List;

public class ArrayDemo {

    public static void main(String[] args) {

        arrayDemo();
        collectionDemo();

    }

    private static void arrayDemo(){
        int[] arr = new int[10];
        String[] students = new String[2];
        students[0]="Ajay";
        students[1]="Dheeraj";
        String[] temp = students;
        students = new String[10];
        for(int i=0; i<temp.length; i++){
            students[i]=temp[i];
        }
        students[2]="Neeraj";
        System.out.println(students[2]);
        System.out.println(students[0]);
        System.out.println(students);
        printArray(students);
    }

    private static void printArray(String[] array){
        for(String str: array){
            System.out.println(str);
        }
    }

    private static void collectionDemo(){
        List<String> stringList = new ArrayList<>();
        stringList.add("Ajay");
        stringList.add("Dheeraj");
        stringList.add("Neeraj");
        System.out.println(stringList);
    }
}
