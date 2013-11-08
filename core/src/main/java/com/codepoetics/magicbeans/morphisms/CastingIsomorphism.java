package com.codepoetics.magicbeans.morphisms;

public class CastingIsomorphism<A, B> implements Isomorphism<A, B> {

    public static <A, B> CastingIsomorphism<A, B> casting() {
        return new CastingIsomorphism<A, B>();
    }
    
    public static <A, B> CastingIsomorphism<A, B> casting(Class<A> fromClass, Class<B> toClass) {
        return new CastingIsomorphism<A, B>();
    }
    
    @Override
    public B to(A value) {
        return Cast.of(value);
    }

    @Override
    public A from(B value) {
        return Cast.of(value);
    }
}
