package com.codepoetics.magicbeans.morphisms;

import com.google.common.base.Function;

public class RoundTripper<A, B> implements Function<A, A> {

    public static <A, B> RoundTripper<A, B> forIso(Isomorphism<A, B> iso) {
        return new RoundTripper<A, B>(iso);
    }
    
    private final Isomorphism<A, B> iso;
    private RoundTripper(Isomorphism<A, B> iso) {
        this.iso = iso;
    }
    @Override
    public A apply(A value) {
        return iso.from(iso.to(value));
    }
}
