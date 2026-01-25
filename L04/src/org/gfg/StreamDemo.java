package org.gfg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo {

    public static void main(String[] args) {

//        List<Integer> integerList = new ArrayList<>(); //Arrays.asList(25,45,60,77,2,8,10);
//        integerList.add(20);
//        Predicate<Integer> checkEven = (x) -> x%2==0;
//        long count  = integerList.stream().filter(checkEven.negate()).count();
//        System.out.println(count);
//
//        count  = integerList.stream().filter((x) -> x%5==0).filter(checkEven).count();
//        System.out.println(count);
//
//
//        Consumer<Integer> print = (i) -> System.out.println(i);
//        System.out.println("Multiply by 2:");
//        Stream<Integer> integerStream = integerList.stream().filter(checkEven).map((x) -> x*2).peek(print);
//
//        integerList.add(30);
//        System.out.println("===========");
//
//        List<Integer> result = integerStream.collect(Collectors.toList());
//
//        count = integerList.stream().count();
//
//        //count = integerStream.count();
//        //System.out.println(count);
//
//        count = integerList.stream().count();
//        System.out.println(count);



        List<String> stringList = Arrays.asList("abc","weer","pop","xyz","abc","weer","pop","xyz","abc","weer","pop","xyz","abc","weer","pop","xyz","abc","weer","pop","xyz");


        long countFor3 = stringList.stream().filter((s) -> s.length()==3).map((s)->s.toUpperCase()).peek((s)-> System.out.println(s)).count();
        System.out.println(countFor3);

        List<Integer> lenList  = stringList.stream().map((s)-> s.length()).collect(Collectors.toList());
        System.out.println(lenList);


        //Sequence Execution
        long start = System.nanoTime();
        countFor3 = stringList.stream().filter((s) -> s.length()<10).map((s)->s.toUpperCase()).peek((s)-> System.out.println(s+":"+Thread.currentThread())).count();
        long end = System.nanoTime();
        System.out.println((end-start)+" ms");

        start = System.nanoTime();
        countFor3 = stringList.parallelStream().filter((s) -> s.length()<10).map((s)->s.toUpperCase()).peek((s)-> System.out.println(s+":"+Thread.currentThread())).count();
        end = System.nanoTime();
        System.out.println((end-start)+" ms");

    }
}
