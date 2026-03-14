package com.example.L15_UTandITdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    public void setup(){
        calculator = new Calculator();
    }


    @Test
    public void testAddPositiveNum(){
        Integer a =4;
        Integer b =5;
        Integer expected= 9;

//        AssertionsForClassTypes.assertThat(calculator.add(a,b)).isEqualTo(expected);
        assertThat(calculator.add(a,b)).isEqualTo(expected);
    }


    @Test
    public void testAddNegativeNum(){
        Integer a =-4;
        Integer b =-5;
        Integer expected= -9;

        assertThat(calculator.add(a,b)).isEqualTo(expected);
    }

    @Test
    public void testMultiplyWithPositiveNum(){
        Integer a = 4;
        Integer b = 3;
        Integer expected = 12;
        assertThat(calculator.multiply(a,b)).isEqualTo(expected);
    }





}
