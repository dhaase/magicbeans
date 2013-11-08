package com.codepoetics.magicbeans.introspection;

import java.util.Map;

import com.codepoetics.magicbeans.morphisms.Isomorphism;
import com.google.common.base.Predicate;

public class PropertyMapIso<T> implements Isomorphism<T, Map<String, Object>> {

    public static <T> PropertyMapIso<T> shallow(Class<T> beanClass) {
        return new PropertyMapIso<T>(ShallowBeanReader.forClass(beanClass), ShallowBeanWriter.forClass(beanClass));
    }
    
    public static <T> PropertyMapIso<T> deep(Class<T> beanClass) {
        return deep(beanClass, NonScalar.instance);
    }
    
    public static <T> PropertyMapIso<T> deep(Class<T> beanClass, Predicate<Class<?>> isBean) {
        return new PropertyMapIso<T>(DeepBeanReader.forClass(beanClass), DeepBeanWriter.forClass(beanClass, isBean));
    }
    
    private final BeanReader<T> reader;
    private final BeanWriter<T> writer;
    
    private PropertyMapIso(BeanReader<T> reader, BeanWriter<T> writer) {
        this.reader = reader;
        this.writer = writer;
    }
    @Override
    public Map<String, Object> to(T object) {
        return reader.apply(object);
    }

    @Override
    public T from(Map<String, Object> properties) {
        return writer.apply(properties);
    }

}
