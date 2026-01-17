package org.gfg;

public class Student extends Person {

    private Integer regNum;
    String batch;



    public boolean canVote(){
        if(age>18){
            return true;
        }
        return false;
    }

    public Student(Integer regNum, String batch, int age) {
        this.regNum = regNum;
        this.batch = batch;
        this.age = age;
    }
}
