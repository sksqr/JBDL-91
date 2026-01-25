package org.gfg;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SupplierDemo {
    public static void main(String[] args) {

        Supplier<Double> randomValue = () -> Math.random();
        System.out.println(randomValue.get());
        System.out.println(randomValue.get());



        Supplier<List<Person>> supplyPersonList = () -> {
            /*

             */
            List<Person> personList = new ArrayList<>();
            personList.add(new Person("Rahul",23));
            personList.add(new Person("Ajay",22));
            return personList;
        } ;

        System.out.println(supplyPersonList.get());
    }
}
