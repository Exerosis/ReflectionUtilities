package me.exerosis.reflection.data;

public class Triplet<A, B, C> {
    private C _c;
    private B _b;
    private A _a;

    public Triplet(A a, B b, C c) {
        _a = a;
        _b = b;
        _c = c;
    }

    public static <A, B, C> Triplet<A, B, C> of(A a, B b, C c) {
        return new Triplet<>(a, b, c);
    }

    public A getA() {
        return _a;
    }

    public Triplet<A, B, C> setA(A a) {
        _a = a;
        return this;
    }

    public B getB() {
        return _b;
    }

    public Triplet<A, B, C> setB(B b) {
        _b = b;
        return this;
    }

    public C getC() {
        return _c;
    }

    public Triplet<A, B, C> setC(C c) {
        _c = c;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Triplet<?, ?, ?>))
            return false;
        Triplet<?, ?, ?> toCheck = (Triplet<?, ?, ?>) obj;
        return _a.equals(toCheck.getA()) && _b.equals(toCheck.getB()) && _c.equals(toCheck.getC());
    }
}
