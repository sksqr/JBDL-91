package org.gfg.jbdl91;

public class DIDemo {

    public static void main(String[] args) {
        Car car1 = new Car();
        Car car2 = new Car();
        System.out.println(car1);
        System.out.println(car2);

        Engine engine = new Engine("Revetron",1200);

        Car car3 = new Car("Tata Hexa", engine); // constructor based DI
        System.out.println(car3);

        car2.setName("Tata Punch");
        car2.setEngine(new Engine("Punch Engine",1400)); // setter based DI


    }
}
