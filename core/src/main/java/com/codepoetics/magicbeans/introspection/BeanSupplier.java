package com.codepoetics.magicbeans.introspection;

import com.google.common.base.Supplier;

public class BeanSupplier<T> implements Supplier<T> {

    public static <T> BeanSupplier<T> forClass(Class<T> beanClass) {
        return new BeanSupplier<T>(beanClass);
    }
    
    private final Class<T> beanClass;
    private BeanSupplier(Class<T> beanClass) {
        this.beanClass = beanClass;
    }
    
    @Override
    public T get() {
        try {
            return beanClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
}
