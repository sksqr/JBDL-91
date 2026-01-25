package org.gfg;

@FunctionalInterface
public interface Calculator<T> {
    T calculate(T a, T b);
}
