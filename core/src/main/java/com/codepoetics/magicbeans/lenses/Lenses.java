package com.codepoetics.magicbeans.lenses;

import com.google.common.base.Function;

public final class Lenses {

    private Lenses() { }
    
    public <A, B, C> Lens<A, C> compose(final Lens<A, B> first, final Lens<B, C> second) {
        return new ComposedLens<A, B, C>(second, first);
    }

    public static <T, P> Function<T, T> updater(Lens<T, P> lens, Function<P, P> updater) {
        return new LensUpdater<T, P>(lens, updater);
    }

    public static <T, P> T update(T value, Lens<T, P> lens, Function<P, P> updater) {
        return update(lens.apply(value), updater);
    }
    
    public static <T, P> T update(Lensed<T, P> lensed, Function<P, P> updater) {
        return lensed.apply(updater.apply(lensed.get()));
    }
}
