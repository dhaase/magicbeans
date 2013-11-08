package com.codepoetics.magicbeans.tuples;

import java.util.Iterator;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public final class Pairs {

    private Pairs() { }
    
    @SuppressWarnings("unchecked")
    public static <A, B> Iterable<Pair<A, B>> fromVarArgs(Object...varargs) {
        Preconditions.checkArgument(varargs.length % 2 == 0, "Odd number of arguments in call to paired argument constructor.");
        ImmutableList.Builder<Pair<A, B>> builder = ImmutableList.builder();
        Iterator<Object> iterator = Lists.newArrayList(varargs).iterator();
        
        while (iterator.hasNext()) {
            A first = (A) iterator.next();
            B second = (B) iterator.next();
            builder.add(Pair.of(first, second));
        }
        
        return builder.build();
    }
}
