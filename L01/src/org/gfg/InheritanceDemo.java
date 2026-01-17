package org.gfg;

public class InheritanceDemo {

    public static void main(String[] args) {

        //Teacher t1 = new Teacher("Maths");
        Person t1 = new Teacher("Maths");
        t1.setName("Ravi");
        t1.walk();
        //t1.teaching();


        //Teacher t2 = new Person();

        Teacher t3 = (Teacher) t1;
        t3.teaching();




    }
}
