package org.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErrorDemo {

    static long count=0;

    public static void main(String[] args) {
        testStackOverFlowError();
    }

    // HeapOutOfMemoryError
    private static void  generateObjects(){
        List<Date> dateList = new ArrayList<>();
        while (true){
            Date date = new Date();
            dateList.add(date);
        }
    }

    // StackOverFlowError

    private static void testStackOverFlowError(){
        count++;
        System.out.println("Calling again :"+count);
        testStackOverFlowError();
    }


}

