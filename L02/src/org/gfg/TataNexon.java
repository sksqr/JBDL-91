package org.gfg;

public class TataNexon extends TataCar {

    private String gps;

    public TataNexon(Engine engine, String gps) {
        super(engine);
        this.gps = gps;
    }

    @Override
    public void displayRC() {

    }

    @Override
    public void displayPUC() {

    }

    @Override
    public void displayInsurance() {

    }

    @Override
    public String getLocation() {
        return "GPS Location:"+gps;
    }

    @Override
    void startCar() {

    }

    @Override
    void stopCar() {

    }
}
