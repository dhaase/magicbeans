package com.codepoetics.magicbeans.introspection;

import com.codepoetics.magicbeans.morphisms.RoundTripper;
import com.google.common.base.Function;
import com.google.common.base.Predicate;

public final class BeanCloner {

    private BeanCloner() { }
    
    public static <T> T shallowClone(T instance) {
        return shallow(TheClass.of(instance)).apply(instance);
    }
    
    public static <T> Function<T, T> shallow(Class<T> beanClass) {
        return RoundTripper.forIso(PropertyMapIso.shallow(beanClass));
    }
    
    public static <T> T deepClone(T instance) {
        return deepClone(instance, NonScalar.instance);
    }
    
    public static <T> T deepClone(T instance, Predicate<Class<?>> isBean) {
        return deep(TheClass.of(instance), isBean).apply(instance);
    }
    
    public static <T> Function<T, T> deep(Class<T> beanClass) {
        return deep(beanClass, NonScalar.instance);
    }
    
    public static <T> Function<T, T> deep(Class<T> beanClass, Predicate<Class<?>> isBean) {
        return RoundTripper.forIso(PropertyMapIso.deep(beanClass, isBean));
    }
    
}
