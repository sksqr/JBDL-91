package org.gfg.jbdl91;

public class Engine {

    private String name;

    private int cc;

    public Engine(String name, int cc) {
        System.out.println("Executing parameterized constructor of Engine class");
        this.name = name;
        this.cc = cc;
    }

    public Engine() {
        System.out.println("Executing constructor of Engine class");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "name='" + name + '\'' +
                ", cc=" + cc +
                '}';
    }

    public void engineInit() {
        System.out.println("Engine initialized");
    }

}