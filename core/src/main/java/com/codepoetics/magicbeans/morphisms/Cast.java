package com.codepoetics.magicbeans.morphisms;

import com.google.common.base.Function;

public class Cast<A, B> implements Function<A, B> {

    @SuppressWarnings("unchecked")
    public static <A, B> B of(A value) {
        return (B) value;
    }
    
    @SuppressWarnings("unchecked")
    public static <A, B> B of(A value, Class<B> toClass) {
        return (B) value;
    }
    
    public static <A, B> Cast<A, B> function() {
        return new Cast<A, B>();
    }
    
    public static <A, B> Cast<A, B> function(Class<A> fromClass, Class<B> toClass) {
        return new Cast<A, B>();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public B apply(A arg0) {
        return (B) arg0;
    }

}
