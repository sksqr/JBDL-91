package org.exceptiondemo;

public class ProductNotFoundException extends Exception{

    String productName;

    public ProductNotFoundException(String message, String productName) {
        super(message);
        this.productName = productName;
    }
}
