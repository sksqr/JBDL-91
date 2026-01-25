package org.gfg;

import org.gfg.Student;

import java.util.Comparator;

public class StudentComparatorOnMarks implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return o1.getMarks() - o2.getMarks();
    }
}
