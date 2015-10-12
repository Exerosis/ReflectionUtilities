package me.exerosis.reflection.data;

public class OrPair<A, B> extends Pair<A, B> {
    public OrPair(A a, B b) {
        super(a, b);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair<?, ?>))
            return false;
        Pair<?, ?> toCheck = (Pair<?, ?>) obj;
        return getA().equals(toCheck.getA()) || getB().equals(toCheck.getB());
    }
}