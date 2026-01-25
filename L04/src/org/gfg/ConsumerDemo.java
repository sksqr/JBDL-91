package org.gfg;

import java.util.function.Consumer;

public class ConsumerDemo {

    public static void main(String[] args) {

        Consumer<Person> printName = (p) -> System.out.println(p.getName());

        printName.accept(new Person("Parul",20));


        Consumer<String> printLowerCase = (s) -> System.out.println(s.toLowerCase());
        Consumer<String> printLen = (s) -> System.out.println(s.length());
        printLowerCase.andThen(printLen).accept("AWS");
    }
}
