package org.gfg;

public class ObjectClassDemo {

    public static void main(String[] args) {

        Object obj1 = new String("Test");

        Object obj2 = new Laptop("HP", new CPU(), new RAM(), new Windows());


        Laptop laptop1 = new Laptop("Dell-123", new CPU(), new RAM(), new Windows());

        System.out.println(laptop1);








    }
}
