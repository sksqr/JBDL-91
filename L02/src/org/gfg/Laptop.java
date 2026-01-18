package org.gfg;

public class Laptop implements IndianGovtITNorms, ChinaGovtITNorms {


    private String model;
    private CPU cpu;
    private RAM ram;

    private OS os;

    public Laptop(String model, CPU cpu, RAM ram, OS os) {
        this.model = model;
        this.cpu = cpu;
        this.ram = ram;
        this.os = os;
    }

    @Override
    public void specificToIndia() {

    }

    @Override
    public void specificToChina() {

    }

    @Override
    public void specificToIndianEnv() {

    }


    @Override
    public String toString() {
        return "Laptop{" +
                "model='" + model + '\'' +
                '}';
    }
}
