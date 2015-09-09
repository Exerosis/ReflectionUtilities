package me.exerosis.reflection.data;

public class Quadruple<A, B, C, D> {
    private D _d;
    private C _c;
    private B _b;
    private A _a;

    public Quadruple(A a, B b, C c, D d) {
        _a = a;
        _b = b;
        _c = c;
        _d = d;
    }

    public static <A, B, C, D> Quadruple<A, B, C, D> of(A a, B b, C c, D d) {
        return new Quadruple<>(a, b, c, d);
    }

    public A getA() {
        return _a;
    }

    public Quadruple<A, B, C, D> setA(A a) {
        _a = a;
        return this;
    }

    public B getB() {
        return _b;
    }

    public Quadruple<A, B, C, D> setB(B b) {
        _b = b;
        return this;
    }

    public C getC() {
        return _c;
    }

    public Quadruple<A, B, C, D> setC(C c) {
        _c = c;
        return this;
    }

    public D getD() {
        return _d;
    }

    public Quadruple<A, B, C, D> setD(D d) {
        _d = d;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Quadruple<?, ?, ?, ?>))
            return false;
        Quadruple<?, ?, ?, ?> toCheck = (Quadruple<?, ?, ?, ?>) obj;
        return _a.equals(toCheck.getA()) && _b.equals(toCheck.getB()) && _c.equals(toCheck.getC()) && _d.equals(toCheck.getD());
    }
}
