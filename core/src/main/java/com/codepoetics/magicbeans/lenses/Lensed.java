package com.codepoetics.magicbeans.lenses;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

public interface Lensed<T, P> extends Supplier<P>, Function<P, T> { }
