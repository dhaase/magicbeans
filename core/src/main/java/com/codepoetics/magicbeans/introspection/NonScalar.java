package com.codepoetics.magicbeans.introspection;

import java.lang.reflect.Constructor;

import com.google.common.base.Predicate;

public final class NonScalar implements Predicate<Class<?>> {
    
    public static final Predicate<Class<?>> instance = new NonScalar();
    
    @Override
    public boolean apply(Class<?> klass) {
        return !((klass.isPrimitive())
                || klass.getName().startsWith("java")
                || hasNoPublicDefaultConstructor(klass));
    }

    private boolean hasNoPublicDefaultConstructor(Class<?> klass) {
        for (Constructor<?> c : klass.getConstructors()) {
            if (c.getParameterTypes().length == 0) return false;
        }
        return true;
    }
}