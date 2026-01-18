package org.exception;

import java.util.Scanner;

public class ExceptionDemo {


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a String:");
        String str = scanner.nextLine();
        if(str.length() >= 5){
            System.out.println("5th Char:"+str.charAt(4));
        }
        System.out.println("Done");

    }
}
