package com.codepoetics.magicbeans.morphisms;

import com.codepoetics.magicbeans.lenses.Lens;
import com.codepoetics.magicbeans.lenses.Lensed;

class IsomorphismWrappedLens<T1, T2, P> implements Lens<T1, P> {
    
    private Lens<T2, P> lens;
    private Isomorphism<T1, T2> iso;
    IsomorphismWrappedLens(Lens<T2, P> lens, Isomorphism<T1, T2> iso) {
        this.lens = lens;
        this.iso = iso;
    }
    
    @Override
    public Lensed<T1, P> apply(T1 item) {
        T2 wrappedItem = iso.to(item);
        return new WrappedLensed(lens.apply(wrappedItem));
    }
    
    private class WrappedLensed implements Lensed<T1, P> {

        private final Lensed<T2, P> wrappedLensed;
        public WrappedLensed(Lensed<T2, P> wrappedLensed) {
            this.wrappedLensed = wrappedLensed;
        }
        @Override
        public P get() {
            return wrappedLensed.get();
        }
        
        @Override
        public T1 apply(P arg0) {
            return iso.from(wrappedLensed.apply(arg0));
        }
        
    }
}