package com.test;

import org.gfg.Person;

public class TestTeacher extends Person {

    public boolean canVote(){
        if(age > 18){
            return true;
        }
        return false;
    }
}
