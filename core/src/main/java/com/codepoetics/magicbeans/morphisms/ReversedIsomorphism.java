package com.codepoetics.magicbeans.morphisms;

final class ReversedIsomorphism<F, G> implements Isomorphism<F, G> {

    private final Isomorphism<G, F> iso;
    public ReversedIsomorphism(Isomorphism<G, F> iso) {
        this.iso = iso;
    }
    
    public Isomorphism<G, F> reverse() {
        return iso;
    }

    @Override
    public G to(F value) {
        return iso.from(value);
    }

    @Override
    public F from(G value) {
        return iso.to(value);
    }
}
