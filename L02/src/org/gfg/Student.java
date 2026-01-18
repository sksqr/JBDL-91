package org.gfg;

import java.util.List;

public class Student {

    private String name;

    private Long rNum;

    private List<Integer> marks;

    transient private double percentage;


    public Long getrNum() {
        return rNum;
    }

    public void setrNum(Long rNum) {
        this.rNum = rNum;
    }

    public List<Integer> getMarks() {
        return marks;
    }

    public void setMarks(List<Integer> marks) {
        this.marks = marks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
