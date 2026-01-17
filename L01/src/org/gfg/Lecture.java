package org.gfg;

public class Lecture {
    public String title;
    String mentor;
    String status;
    int no; // inside object
    Integer number; // in heap

    public static Integer objectCount=0;

    public Lecture(String title, String mentor, String status) {
        this.title = title;
        this.mentor = mentor;
        this.status = status;
        objectCount++;
    }

    public Lecture() {
        objectCount++;
    }

    public void joinNow(){

    }

    @Override
    public String toString() {
        return "Lecture{" +
                "title='" + title + '\'' +
                ", mentor='" + mentor + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
