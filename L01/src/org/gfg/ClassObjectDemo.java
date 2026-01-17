package org.gfg;

public class ClassObjectDemo {
    public static void main(String[] args) {

        testMethod();
        System.out.println("Object Count:"+Lecture.objectCount);




    }

    private static void testMethod(){
        Lecture l1 = new Lecture();
        l1.mentor ="Shashi";
        l1.title = "Java basics";
        l1.status = "live";

        System.out.println(l1);

        Lecture l2 = new Lecture();
        l2.mentor ="Shashi";
        l2.title = "Java Advance";
        l2.status = "Upcoming";


        Lecture l3 = new Lecture("L3","shashi","Upcoming");

        System.out.println("Object Count:"+l1.objectCount);
        System.out.println("Object Count:"+Lecture.objectCount);

    }
}
