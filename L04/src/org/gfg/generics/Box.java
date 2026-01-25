package org.gfg.generics;

public class Box<T> {

    private T t;

    public T getItem() {
        return t;
    }

    public void setItem(T t) {
        this.t = t;
    }
}
