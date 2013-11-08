package com.codepoetics.magicbeans.morphisms;

final class ComposedIsomorphism<A, C, B> implements
        Isomorphism<A, C> {
    private final Isomorphism<A, B> first;
    private final Isomorphism<B, C> second;

    ComposedIsomorphism(Isomorphism<A, B> first, Isomorphism<B, C> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public C to(A value) {
        return second.to(first.to(value));
    }

    @Override
    public A from(C value) {
        return first.from(second.from(value));
    }
}