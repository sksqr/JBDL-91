package org.collections;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetDemo {
    public static void main(String[] args) {

//        Set<Integer> set = new HashSet<>();
//        set.add(1);
//        set.add(2);
//        set.add(2);
//        set.add(3);
//        set.add(4);
//        set.add(4);
//        System.out.println(set.size());
//        System.out.println(set);

        System.out.println("TreeSet");
        Set<Integer> set = new TreeSet<>();
        set.add(3);
        set.add(1);
        set.add(2);
        set.add(2);
        set.add(4);
        set.add(4);
        System.out.println(set.size());
        System.out.println(set);




        //Set<Student> students = new HashSet<>();
        Set<Student> students = new TreeSet<>((s1,s2) ->  s1.getRollNo() - s2.getRollNo());
        Student student1 = new Student(20,"Vjay");
        Student student2 = new Student(20,"Vjay");

        System.out.println(student1 == student2);
        System.out.println(student1.equals(student2));
        students.add(new Student(3,"Ravi"));
        students.add(new Student(200,"Rahul"));
        students.add(new Student(31,"Ajay"));
        students.add(student1);
        students.add(student2);
        System.out.println(students.size());
        System.out.println(students);

    }
}
