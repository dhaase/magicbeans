package com.codepoetics.magicbeans.introspection;

public final class TheClass {

    private TheClass() { }
    
    @SuppressWarnings("unchecked")
    public static <T> Class<T> of(T instance) {
        return (Class<T>) instance.getClass();
    }
}
