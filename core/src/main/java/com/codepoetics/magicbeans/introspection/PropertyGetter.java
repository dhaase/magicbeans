package com.codepoetics.magicbeans.introspection;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class PropertyGetter<T, P> implements Getter<T, P> {

    public static <T, P> PropertyGetter<T, P> forDescriptor(PropertyDescriptor pd) {
        return new PropertyGetter<T, P>(pd.getReadMethod());
    }
    
    private final Method readMethod;
    
    private PropertyGetter(Method readMethod) {
        this.readMethod = readMethod;
    }

    @SuppressWarnings("unchecked")
    @Override
    public P apply(T item) {
        try {
            return (P) readMethod.invoke(item);
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
