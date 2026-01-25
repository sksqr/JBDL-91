package org.gfg.generics;

import org.gfg.Student;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class NonGenericDemo {

    public static void main(String[] args) {


        Set<Object> set = new HashSet();
        set.add(new Student(1,"Ravi"));
        set.add(1);
        set.add("Shashi");


        Set<Integer> integerSet = new HashSet<>();

        integerSet.add(1);
        //integerSet.add("asdf");

    }
}
