package com.codepoetics.magicbeans.introspection;

public interface Setter<T, P> {
    void set(T instance, P value);
}
