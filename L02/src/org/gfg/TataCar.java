package org.gfg;

public abstract class TataCar implements IndianGovtMotorRules {

    private Engine engine;

    public TataCar(Engine engine) {
        this.engine = engine;
    }

    public void tataSpecificMethod(){
        System.out.println("tataSpecificMethod");
    }

    public final String companyName(){
        return "Tata Motors";
    }


    abstract void startCar();

    abstract void stopCar();
}
