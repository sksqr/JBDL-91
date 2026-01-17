package org.gfg;

public class Teacher extends Person{

    private String subject;

    public Teacher(String subject) {
        this.subject = subject;
    }

    public Teacher() {
        //super();
    }

    @Override
    public void walk(){
        super.walk();
        System.out.println("Teacher "+getName()+" is walking");
    }


    public void teaching(){
        System.out.println("Teacher "+getName()+" is teaching");
    }




}
