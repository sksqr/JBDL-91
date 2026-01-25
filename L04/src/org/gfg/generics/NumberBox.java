package org.gfg.generics;

public class NumberBox <T extends Number>{

    private T t;

    public T getItem() {
        return t;
    }

    public void setItem(T t) {
        this.t = t;
    }
}
