package org.gfg;

import java.util.*;

public class ComparableAndComparatorDemo {

    public static void main(String[] args) {

        List<Integer> integerList = new ArrayList<>();

        List<Student> students = new ArrayList<>();
        students.add(new Student(3,"Ravi",77));
        students.add(new Student(200,"Rahul",87));
        students.add(new Student(31,"Ajay",72));
        students.add(new Student(20,"Vjay",81));

//        Collections.sort(integerList);
        Collections.sort(students);

        System.out.println(students);

        //Collections.sort(students,new StudentComparatorOnMarks());
        Collections.sort(students,(s1,s2) -> s1.getMarks()-s2.getMarks());
        System.out.println(students);

    }
}
