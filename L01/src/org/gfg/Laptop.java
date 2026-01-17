package org.gfg;


public class Laptop {

    private Keyboard keyboard;
    private OS os;
    private String company;

    public Laptop(Keyboard keyboard, OS os, String company) {
        this.keyboard = keyboard;
        this.os = os;
        this.company = company;
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "keyboard=" + keyboard +
                ", os=" + os +
                ", company='" + company + '\'' +
                '}';
    }
}
