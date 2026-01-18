package org.gfg;

public enum DayOfWeek {

    SUNDAY("SUN",1), MONDAY("MON",2), TUESDAY("TUE",3), WEDNESDAY("WED",4), THURSDAY("THU",5), FRIDAY("FRI",6), SATURDAY("SAT",7);

    private String value;
    private Integer seq;

    private DayOfWeek(String value, Integer seq) {
        this.value = value;
        this.seq = seq;
    }
}
