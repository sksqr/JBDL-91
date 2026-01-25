package org.gfg.generics;

import org.gfg.Calculator;

public class FunctionalInterfaceDemo {

    public static void main(String[] args) {

//        Calculator<Integer> intAddCalculator = new Calculator<Integer>() {
//            @Override
//            public Integer calculate(Integer a, Integer b) {
//                return a+b;
//            }
//        };

        Calculator<Integer> intAddCalculator = (i1,i2) -> i1+i2;

        System.out.println(intAddCalculator.calculate(3,4));
        System.out.println(intAddCalculator.calculate(30,40));


        Calculator<String> addStringCalculator = (s1,s2) -> s1+s2;
        System.out.println(addStringCalculator.calculate("Java"," GFG"));
    }
}
