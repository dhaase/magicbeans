package com.codepoetics.magicbeans.morphisms;

import com.codepoetics.magicbeans.lenses.Lens;
import com.codepoetics.magicbeans.lenses.Lensed;

class IsomorphismComposedLens<A, B, C> implements Lens<A, C> {
    
    private Lens<A, B> lens;
    private Isomorphism<B, C> iso;
    IsomorphismComposedLens(Lens<A, B> lens, Isomorphism<B, C> iso) {
        this.lens = lens;
        this.iso = iso;
    }
    
    @Override
    public Lensed<A, C> apply(A item) {
        return new ComposedLensed(lens.apply(item));
    }
    
    private class ComposedLensed implements Lensed<A, C> {

        private final Lensed<A, B> wrappedLensed;
        public ComposedLensed(Lensed<A, B> wrappedLensed) {
            this.wrappedLensed = wrappedLensed;
        }
        @Override
        public C get() {
            return iso.to(wrappedLensed.get());
        }
        
        @Override
        public A apply(C arg0) {
            return wrappedLensed.apply(iso.from(arg0));
        }
        
    }
}