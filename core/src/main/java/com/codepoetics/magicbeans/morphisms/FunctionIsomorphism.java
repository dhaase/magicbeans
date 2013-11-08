package com.codepoetics.magicbeans.morphisms;

import com.google.common.base.Function;

final class FunctionIsomorphism<F, G> implements Isomorphism<F, G> {
    private final Function<G, F> from;
    private final Function<F, G> to;

    FunctionIsomorphism(Function<G, F> from, Function<F, G> to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public G to(F value) {
        return to.apply(value);
    }

    @Override
    public F from(G value) {
        return from.apply(value);
    }
}