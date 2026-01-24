package org.exceptiondemo;

import java.util.Scanner;

public class RuntimeExceptionCatchDemo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a String:");
        String str = scanner.nextLine();
        try {
            System.out.println("5th Char:" + str.charAt(4));
        }
        catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println("Done");
    }
}
