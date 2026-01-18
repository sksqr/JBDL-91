package org.gfg;

public class InterfaceDemo {


    public static void main(String[] args) {

        OS ubuntuOS = new Ubuntu();

        OS windowsOS = new Windows();



        Laptop laptop1 = new Laptop("Dell-123",new CPU(), new RAM(), windowsOS);

        Laptop laptop2 = new Laptop("Dell-123",new CPU(), new RAM(), ubuntuOS);

        Laptop laptop3 = new Laptop("Dell-123",new CPU(), new RAM(), new MintKali());


    }
}
