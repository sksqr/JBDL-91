package org.gfg;

public class EnumDemo {

    public static void main(String[] args) {

        Lecture l2 = new Lecture("L2", "Shashi", Status.LIVE);

        //Status status = new Status("test");

        Lecture l3 = new Lecture("L3", "Shashi", Status.UPCOMING);

        System.out.println(l3);

        Lecture l4 = new Lecture("L4", "Shashi", Status.UPCOMING);
        System.out.println(l4);


        DayOfWeek day1 = DayOfWeek.SUNDAY;
        DayOfWeek day2 = DayOfWeek.SATURDAY;
    }
}
