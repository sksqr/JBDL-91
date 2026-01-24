package org.collections;

import java.util.*;

public class CollectionUtilDemo {

    public static void main(String[] args) {

        List<Student> students = new ArrayList<>();

        Student student1 = new Student(20,"Vjay");
        Student student2 = new Student(20,"Vjay");

        System.out.println(student1 == student2);
        System.out.println(student1.equals(student2));
        students.add(new Student(3,"Ravi"));
        students.add(new Student(200,"Rahul"));
        students.add(new Student(31,"Ajay"));
        students.add(student1);
        students.add(student2);

        System.out.println(students);
        Collections.sort(students, (s1,s2)-> s1.getRollNo() - s2.getRollNo());
        System.out.println(students);

    }
}
