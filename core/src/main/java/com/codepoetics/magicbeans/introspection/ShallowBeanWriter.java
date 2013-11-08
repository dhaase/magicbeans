package com.codepoetics.magicbeans.introspection;

import java.util.Map;

import com.google.common.base.Supplier;

public class ShallowBeanWriter<T> implements BeanWriter<T> {

    public static <T> ShallowBeanWriter<T> forClass(Class<T> beanClass) {
        return new ShallowBeanWriter<T>(BeanSupplier.forClass(beanClass), PropertyAccessors.forClass(beanClass));
    }
    
    private final Supplier<T> beanSupplier;
    private final Map<String, Accessor<T, ?>> accessors;
    
    private ShallowBeanWriter(Supplier<T> beanSupplier, Map<String, Accessor<T, ?>> accessors) {
        this.beanSupplier = beanSupplier;
        this.accessors = accessors;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public T apply(Map<String, Object> properties) {
        T instance = beanSupplier.get();
        
        for (Map.Entry<String, Accessor<T, ?>> entry : accessors.entrySet()) {
            String propertyName = entry.getKey();
            PropertyAccessor<T, Object> accessor = (PropertyAccessor<T, Object>) entry.getValue();
            Object propertyValue = properties.get(propertyName);
            
            accessor.set(instance, propertyValue);
        }
        
        return instance;
    }
}
