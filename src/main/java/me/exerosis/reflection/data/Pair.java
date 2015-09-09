package me.exerosis.reflection.data;

public class Pair<A, B> {
    private B _b;
    private A _a;

    public Pair(A a, B b) {
        _a = a;
        _b = b;
    }

    public static <A, B> Pair<A, B> of(A a, B b) {
        return new Pair<>(a, b);
    }

    public A getA() {
        return _a;
    }

    public Pair<A, B> setA(A key) {
        _a = key;
        return this;
    }

    public B getB() {
        return _b;
    }

    public Pair<A, B> setB(B value) {
        _b = value;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair<?, ?>))
            return false;
        Pair<?, ?> toCheck = (Pair<?, ?>) obj;
        return _a.equals(toCheck.getA()) && _b.equals(toCheck.getB());
    }
}