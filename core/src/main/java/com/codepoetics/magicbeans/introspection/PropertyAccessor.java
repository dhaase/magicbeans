package com.codepoetics.magicbeans.introspection;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Type;

import com.google.common.base.Optional;


public class PropertyAccessor<T, P> implements Accessor<T, P> {

    @SuppressWarnings("unchecked")
    public static <T, P> Accessor<T, P> forProperty(String propertyName, Class<T> beanClass) {
        return (PropertyAccessor<T, P>) PropertyAccessors.forClass(beanClass).get(propertyName);
    }
    
    public static <T, P> Accessor<T, P> forDescriptor(PropertyDescriptor pd) {
        Getter<T, P> getter = PropertyGetter.forDescriptor(pd);
        Optional<Setter<T, P>> setter = PropertySetter.forDescriptor(pd);
        return new PropertyAccessor<T, P>(pd.getDisplayName(), pd.getReadMethod().getGenericReturnType(), getter, setter);
    }
    
    private final String name;
    private final Type type;
    private final Getter<T, P> getter;
    private final Optional<Setter<T, P>> setter;
    
    public PropertyAccessor(String name, Type type, Getter<T, P> getter, Optional<Setter<T, P>> setter) {
        this.name = name;
        this.type = type;
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public Type type() {
        return type;
    }
    
    @Override
    public P apply(T object) {
        return getter.apply(object);
    }

    @Override
    public void set(T instance, P value) {
        if (!setter.isPresent()) {
            throw new UnsupportedOperationException("No setter for property " + name);
        }
        setter.get().set(instance, value);
    }
}
