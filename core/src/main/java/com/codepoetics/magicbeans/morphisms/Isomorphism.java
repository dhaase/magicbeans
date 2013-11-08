package com.codepoetics.magicbeans.morphisms;

public interface Isomorphism<F, G> {

    G to(F value);
    F from(G value);
    
}
