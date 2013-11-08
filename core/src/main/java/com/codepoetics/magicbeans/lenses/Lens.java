package com.codepoetics.magicbeans.lenses;

import com.google.common.base.Function;

public interface Lens<T, P> extends Function<T, Lensed<T, P>>{ }
