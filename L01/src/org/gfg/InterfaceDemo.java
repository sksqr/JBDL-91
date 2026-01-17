package org.gfg;

public class InterfaceDemo {

    public static void main(String[] args) {
        TestClass obj = new TestClass();
        obj.method1();
    }
}


class TestClass implements InterfaceA, InterfaceB{

    @Override
    public void method1() {
        System.out.println("method1 of  TestClass");
    }

//    @Override
//    public void method1() {
//        System.out.println("method1 of  TestClass");
//    }
    
}

interface InterfaceA{
    public void   method1();
}
interface InterfaceB{
    public void   method1();
}

