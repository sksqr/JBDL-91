package org.gfg;

public class Lecture {

    private String title;
    private String mentor;

    // LIVE, ARCHIVED, UPCOMING
    private Status status;

    public Lecture(String title, String mentor, Status status) {
        this.title = title;
        this.mentor = mentor;
        this.status = status;
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

enum Status{
    LIVE("Live"), ARCHIVED("Archived"), UPCOMING("Upcoming");

    String value;

    private Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
