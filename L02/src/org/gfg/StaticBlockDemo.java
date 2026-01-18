package org.gfg;

public class StaticBlockDemo {

    public StaticBlockDemo() {
        System.out.println("Executing Constructor");
    }

    // This will execute at the time of Class Loading
    static {
        System.out.println("Executing Static Block");
    }


    public static void main(String[] args) {
        StaticBlockDemo obj1 = new StaticBlockDemo();

        StaticBlockDemo obj2 = new StaticBlockDemo();

        StaticBlockDemo obj3 = new StaticBlockDemo();

        Ubuntu ubuntu = new Ubuntu();
    }
}
