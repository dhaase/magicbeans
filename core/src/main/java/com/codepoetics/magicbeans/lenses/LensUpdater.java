package com.codepoetics.magicbeans.lenses;

import com.google.common.base.Function;

class LensUpdater<T, P> implements Function<T, T> {

    private final Lens<T, P> lens;
    private final Function<P, P> updater;
    public LensUpdater(Lens<T, P> lens, Function<P, P> updater) {
        this.lens = lens;
        this.updater = updater;
    }
    
    @Override
    public T apply(T value) {
        return Lenses.update(lens.apply(value), updater);
    }

}
