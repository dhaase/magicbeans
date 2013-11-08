package com.codepoetics.magicbeans.introspection;

import java.lang.reflect.Type;


public interface Accessor<T, P> extends Getter<T, P>, Setter<T, P> {
    Type type();
}
