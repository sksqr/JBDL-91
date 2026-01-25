package org.gfg;

public class Student implements Comparable<Student> {

    private Integer rollNo;

    private String name;

    private Integer marks;

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

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public Student(Integer rollNo, String name) {
        this.rollNo = rollNo;
        this.name = name;
    }

    public Student(Integer rollNo, String name, Integer marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Student{" +
                "rollNo=" + rollNo +
                ", name='" + name + '\'' +
                ", marks=" + marks +
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

    @Override
    public int compareTo(Student s2) {
        return this.rollNo - s2.rollNo;
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
