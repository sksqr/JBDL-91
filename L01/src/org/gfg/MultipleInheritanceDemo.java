package org.gfg;

public class MultipleInheritanceDemo {

    public static void main(String[] args) {
        C c = new C();
        c.method1();
    }
}
class C extends A{

}
class A{
    public void   method1(){
        System.out.println("method1 of A");
    }
}
class B{
    public void   method1(){
        System.out.println("method1 of B");
    }
}