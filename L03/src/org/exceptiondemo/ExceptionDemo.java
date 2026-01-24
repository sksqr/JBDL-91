package org.exceptiondemo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ExceptionDemo {


    public static void main(String[] args) {

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter a String:");
//        String str = scanner.nextLine();
//        //if(str.length() >= 5){
//            System.out.println("5th Char:"+str.charAt(4));
//        //}
//        System.out.println("Done");

        readFromFile();

        try {
            readFromFile2();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    private static void readFromFile(){
        FileWriter fileWriter2 = null;
        try (FileReader fileReader1 = new FileReader("/tmp/test03.txt")){

            int a = fileReader1.read();
            System.out.println(a);


            FileWriter fileWriter = new FileWriter("/tmp/test03.txt");// Suggestion is to use try with resource because FileWriter implements Closable interface
            System.out.println("Writing to file");
            fileWriter.append("c");

            fileWriter2 = new FileWriter("/tmp/test03.txt");// Suggestion is to use try with resource because FileWriter implements Closable interface
            System.out.println("Writing to file");
            fileWriter2.append("c");
            fileWriter2.close(); // If exception at line 42, this will not execute.
            System.out.println("End of try block");

        } catch (FileNotFoundException e) {
            System.out.println("Got FileNotFoundException");
           // throw new RuntimeException(e);
        }
        catch (IOException e) {
            System.out.println("Got IOException");
            //throw new RuntimeException(e);
        }
        finally {
            System.out.println("Executing finally!");
            if(fileWriter2!=null){
                try {
                    fileWriter2.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        System.out.println("Done");


    }


    private static void readFromFile2() throws IOException {
        FileReader fileReader1 = null;
        fileReader1 = new FileReader("/tmp/test03.txt");
        int a = fileReader1.read();
    }
}
