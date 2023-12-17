package org.example.test;

import org.example.T;

public class Test {
    private final String a;

    public Test(String a) {
        this.a = a;
    }

    public T boh() {
        return new T(a);
    }

    public String getA() {
        return a;
    }
}
