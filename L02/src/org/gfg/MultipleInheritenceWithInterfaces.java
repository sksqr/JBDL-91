package org.gfg;

public class MultipleInheritenceWithInterfaces {

    public static void main(String[] args) {

        C c = new C();
        c.method1();

        A a = new C();
        a.method1();

        B b = new C();
        b.method1();
    }
}

class C implements A, B{

    @Override
    public void method1() {
        System.out.println("In class C");
    }

    @Override
    public void method2() {
        //B.super.method2();
        //A.super.method2();
        System.out.println("method 2 of C");
    }
}

interface A{
    void method1();

    default void method2(){
        System.out.println("method 2 of A");
    }
}


interface B{

    void method1();

    default void method2(){
        System.out.println("method 2 of B");
    }
}