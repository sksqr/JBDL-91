package org.gfg;

public class CompositionDemo {


    public static void main(String[] args) {

        OS os =new OS();
        Keyboard keyboard = new Keyboard();

        Laptop laptop1 = new Laptop(keyboard, os, "Dell");
        System.out.println(laptop1);


        Laptop laptop2 = new Laptop(keyboard, os, "Dell");
        System.out.println(laptop2);


        Laptop laptop3 = new Laptop(keyboard, new OS("Ubuntu"), "Dell");
        System.out.println(laptop3);


        Laptop laptop4 = new Laptop(new MagicKeyboard(), os, "Dell");
        System.out.println(laptop3);

    }
}

/*
class DataFormatter

class SearchAPIService{
 private DataFormatter dataFormatter;
}

class ApplyAPIService{
 private DataFormatter dataFormatter;
}
 */