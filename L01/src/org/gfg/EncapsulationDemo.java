package org.gfg;

public class EncapsulationDemo {

    public static void main(String[] args) {

        Student s1 = new Student(434324,"JBDL-91", 19);

        System.out.println(s1.batch);

//        System.out.println(s1.regNum);
//        System.out.println(s1.age);

        System.out.println(s1.canVote());


        Person p1 = new Person();
        p1.setName("Rahul"); // p1.name = "Rahul";
        System.out.println(p1);

    }
}
