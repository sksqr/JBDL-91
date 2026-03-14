package com.example.L15_UTandITdemo;

public class Calculator {


    public Integer add(Integer a, Integer b){
        return a+b;
    }

    public Integer multiply(Integer a, Integer b){
        Integer result =0;
        for(int i=0; i<b; i++){
            result = add(result,a);
        }
        return result;
    }




    public static void main(String[] args) {


        Calculator calculator = new Calculator();

        if(calculator.add(2,3)==5){
            System.out.println("Passed");
        }
        else {
            System.out.println("Failed");
        }

        if(calculator.multiply(2,3)==6){
            System.out.println("Passed");
        }
        else {
            System.out.println("Failed");
        }
    }
}
