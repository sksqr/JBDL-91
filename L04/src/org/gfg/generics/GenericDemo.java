package org.gfg.generics;

import org.gfg.Student;

import java.util.Collections;
import java.util.List;

public class GenericDemo {

    public static void main(String[] args) {
        Box<String> box = new Box<>();
        box.setItem("abc");
        //box.setItem(4);
        Box<Integer> integerBox = new Box<>();
        integerBox.setItem(5);
        Double d = 3.4;
        Integer i=10;

        Box<Student> studentBox = new Box<>();

        NumberBox<Integer> integerNumberBox = new NumberBox<>();
        integerNumberBox.setItem(i);
        //integerNumberBox.setItem(d);

       //NumberBox<String> stringNumberBox = new NumberBox<String>();

        //NumberBox<Student> studentNumberBox = new NumberBox<Student>();


    }
}
