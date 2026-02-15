package com.example.L10_Annotation_RestFul_AOP_demo.lombokdemo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Setter
@Getter
@ToString
public class Student {

    private Integer rollNo;

    private String name;

    public Integer getRollNo() {
        return rollNo;
    }

    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //    public Student() {
//    }
//
//    public Student(Integer rollNo, String name) {
//        this.rollNo = rollNo;
//        this.name = name;
//    }

//    public Integer getRollNo() {
//        return rollNo;
//    }
//
//    public void setRollNo(Integer rollNo) {
//        this.rollNo = rollNo;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    @Override
//    public String toString() {
//        return "Student{" +
//                "rollNo=" + rollNo +
//                ", name='" + name + '\'' +
//                '}';
//    }
//
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
