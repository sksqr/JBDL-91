package org.gfg;

public class SingletonDesignPatternDemo {

    public static void main(String[] args) {

        Principal p1 = Principal.getInstance();
        Principal p2 = Principal.getInstance();

        System.out.println(p1==p2);
    }
}
