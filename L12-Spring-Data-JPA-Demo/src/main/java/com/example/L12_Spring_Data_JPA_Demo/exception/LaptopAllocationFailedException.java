package com.example.L12_Spring_Data_JPA_Demo.exception;


public class LaptopAllocationFailedException extends Exception{

    public LaptopAllocationFailedException(String msg) {
        super(msg);
    }
}