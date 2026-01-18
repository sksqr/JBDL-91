package org.gfg;

public class AbstractClassDemo {


    public static void main(String[] args) {

        Engine rev1200cc = new Engine(1200);
        //TataCar tataCar = new TataCar(rev1200cc);

        TataTiago tataTiago = new TataTiago(rev1200cc, "Herman Music");


        TataPunch tataPunch = new TataPunch(new Engine(1500));

        TataNexon tataNexon = new TataNexon(new Engine(2000),"GPS-123");

        System.out.println(tataPunch.getLocation());


        System.out.println(tataNexon.getLocation());
    }
}
