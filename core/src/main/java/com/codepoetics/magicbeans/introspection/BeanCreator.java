package com.codepoetics.magicbeans.introspection;

import java.util.Map;

import com.codepoetics.magicbeans.maps.EasyMap;
import com.google.common.base.Function;

public final class BeanCreator<T> implements Function<Map<String, Object>, T> {

    public static <T> BeanCreator<T> forClass(Class<T> beanClass) {
        return new BeanCreator<T>(ShallowBeanWriter.forClass(beanClass));
    }
    public static <T> T create(Class<T> beanClass, Object...properties) {
        return forClass(beanClass).create(properties);
    }
    
    private final BeanWriter<T> beanWriter;
    private BeanCreator(BeanWriter<T> beanWriter) {
        this.beanWriter = beanWriter;
    }
    
    public T create(Object...properties) {
        return apply(EasyMap.properties(properties));
    }
    
    public T create(Map<String, Object> propertyMap) {
        return apply(propertyMap);
    }
    
    @Override
    public T apply(Map<String, Object> propertyMap) {
        return beanWriter.apply(propertyMap);
    }
}
