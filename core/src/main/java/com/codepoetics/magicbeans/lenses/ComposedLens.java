package com.codepoetics.magicbeans.lenses;

final class ComposedLens<A, B, C> implements Lens<A, C> {
    private final Lens<B, C> second;
    private final Lens<A, B> first;

    ComposedLens(Lens<B, C> second, Lens<A, B> first) {
        this.second = second;
        this.first = first;
    }

    @Override
    public Lensed<A, C> apply(final A value) {
        return new ComposedLensed<A, B, C>(second.apply(first.apply(value).get()), first.apply(value));
    }
    
    private final static class ComposedLensed<A, B, C> implements Lensed<A, C> {
        private final Lensed<B, C> secondLensed;
        private final Lensed<A, B> firstLensed;

        private ComposedLensed(Lensed<B, C> secondLensed, Lensed<A, B> firstLensed) {
            this.secondLensed = secondLensed;
            this.firstLensed = firstLensed;
        }

        @Override
        public C get() {
            return secondLensed.get();
        }

        @Override
        public A apply(C value) {
            return firstLensed.apply(secondLensed.apply(value));
        }
    }
}