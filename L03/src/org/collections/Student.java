package org.collections;

import java.util.Objects;

public class Student {

    private Integer rollNo;

    private String name;

    public Integer getRollNo() {
        return rollNo;
    }

    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }

    public Student(Integer rollNo, String name) {
        this.rollNo = rollNo;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "rollNo=" + rollNo +
                ", name='" + name + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object obj) {
       Student student = (Student)obj;
        if(this.rollNo.equals(student.rollNo)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return rollNo.hashCode();
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Student student = (Student) o;
//        return Objects.equals(rollNo, student.rollNo) && Objects.equals(name, student.name);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(rollNo, name);
//    }
}
