package org.gfg;

public class Ubuntu implements OS{

    static {
        System.out.println("Static Block og Ubuntu");
    }
    @Override
    public void manageCPU() {
        System.out.println("Ubuntu managing CPU");
    }

    @Override
    public void manageRAM() {

    }

    @Override
    public void manageHD() {

    }

    @Override
    public void manageSSD() {

    }

    @Override
    public void manageNIC() {

    }
}
