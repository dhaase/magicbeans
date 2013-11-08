package com.codepoetics.magicbeans.morphisms;

import com.codepoetics.magicbeans.lenses.Lens;
import com.google.common.base.Function;

public class Isomorphisms {

    public static <F, G> Isomorphism<F, G> fromFunctions(final Function<F, G> to, final Function<G, F> from) {
        return new FunctionIsomorphism<F, G>(from, to);
    }
    
    public static <F, G> Isomorphism<G, F> reverse(final Isomorphism<F, G> iso) {
        if (iso instanceof ReversedIsomorphism) return ((ReversedIsomorphism<F, G>) iso).reverse();
        return new ReversedIsomorphism<G, F>(iso);
    }
    
    public static <T1, T2, P> Lens<T1, P> wrap(Lens<T2, P> lens, Isomorphism<T1, T2> iso) {
        return new IsomorphismWrappedLens<T1, T2, P>(lens, iso);
    }
    
    public static <A, B, C> Isomorphism<A, C> cast(Isomorphism<A, B> iso) {
        return compose(iso, new CastingIsomorphism<B, C>());
    }
    
    public static <A, B, C> Isomorphism<A, C> compose(final Isomorphism<A, B> first, final Isomorphism<B, C> second) {
        return new ComposedIsomorphism<A, C, B>(first, second);
    }
    
    public static <A, B, C> Lens<A, C> compose(Lens<A, B> lens, Isomorphism<B, C> iso) {
        return new IsomorphismComposedLens<A, B, C>(lens, iso);
    }
}
