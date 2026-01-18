package org.gfg;

public class FinalDemo {


    public static void main(String[] args) {
        final int num = 23;
        //num = 56;

        final OS os1 = new Ubuntu();
        //os1 = new Windows();

        OS os2 = new Ubuntu();
        os2 = new Windows();


    }
}

//class CustomString extends String{
//}