package com.codepoetics.magicbeans.introspection;

import org.pcollections.PMap;

import com.codepoetics.magicbeans.maps.EasyMap;

public final class ShallowBeanReader<T> implements BeanReader<T> {

    public static <T> PMap<String, Object> read(T bean) {
        return ShallowBeanReader.forClass(TheClass.of(bean)).apply(bean);
    }
    
    public static <T> ShallowBeanReader<T> forClass(Class<T> beanClass) {
        return new ShallowBeanReader<T>(BeanPropertyEnumerator.forClass(beanClass));
    }

    private final BeanPropertyEnumerator<T> enumerator;
    private ShallowBeanReader(BeanPropertyEnumerator<T> enumerator) {
        this.enumerator = enumerator;
    }
    
    @Override
    public PMap<String, Object> apply(T bean) {
        return EasyMap.of(enumerator.apply(bean));
    }
}
