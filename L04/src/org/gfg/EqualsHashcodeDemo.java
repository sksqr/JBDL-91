package org.gfg;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class EqualsHashcodeDemo {
    public static void main(String[] args) {



        Set<Student> students = new HashSet<>();
        //Set<Student> students = new TreeSet<>((s1,s2) ->  s1.getRollNo() - s2.getRollNo());
        Student student1 = new Student(20,"Vjay");
        Student student2 = new Student(20,"Vjay");
        System.out.println(student1 == student2); // false
        System.out.println(student1.equals(student2)); // true
        students.add(new Student(3,"Ravi"));
        students.add(new Student(200,"Rahul"));
        students.add(new Student(31,"Ajay"));
        students.add(student1);
        students.add(student2);
        System.out.println(students.size());
        System.out.println(students);

    }
}
