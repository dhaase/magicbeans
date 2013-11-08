package com.codepoetics.magicbeans.tuples;

import com.google.common.base.Function;

public class Pair<A, B> {

    public static <A, B> Pair<A, B> of(A first, B second) {
        return new Pair<A, B>(first, second);
    }
    
    public static <A, B, C> Function<Pair<A, B>, Pair<A, C>> secondConverter(final Function<B, C> converter) {
        return new Function<Pair<A, B>, Pair<A, C>>() {
            @Override
            public Pair<A, C> apply(Pair<A, B> pair) {
                return Pair.of(pair.first, converter.apply(pair.second));
            }
        };
    }
    
    public final A first;
    public final B second;
    
    private Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }
}
