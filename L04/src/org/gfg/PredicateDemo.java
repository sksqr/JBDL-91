package org.gfg;

import java.util.function.Predicate;

public class PredicateDemo {

    public static void main(String[] args) {

        Predicate<Integer> checkEven = (x) -> x%2==0;

        Predicate<Integer> divisibleBy3 = (x) -> x%3==0;

        Predicate<Integer> divisibleBy6 = checkEven.and(divisibleBy3);


        System.out.println(checkEven.test(5));
        System.out.println(divisibleBy3.test(6));

        Predicate<Person> checkVoterElgibility = (p) -> p.getAge() >=18;

        Person p1 = new Person("Parul",19);
        System.out.println(checkVoterElgibility.test(p1));
    }
}
