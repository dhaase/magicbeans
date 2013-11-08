package com.codepoetics.magicbeans.introspection;

import static com.google.common.collect.Iterables.transform;

import java.util.Collection;
import java.util.Map;

import org.pcollections.PMap;
import org.pcollections.TreePVector;

import com.codepoetics.magicbeans.maps.EasyMap;
import com.codepoetics.magicbeans.tuples.Pair;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public final class DeepBeanReader<T> implements BeanReader<T> {

    public static final Predicate<Class<?>> nonScalar = new NonScalar();
    
    public static <T> PMap<String, Object> read(T value) {
        return read(value, nonScalar);
    }
    
    public static <T> PMap<String, Object >read(T value, Predicate<Class<?>> isBean) {
        return forClass(TheClass.of(value), isBean).apply(value);
    }
    
    public static <T> DeepBeanReader<T> forClass(Class<T> beanClass) {
        return forClass(beanClass, nonScalar);
    }
    
    public static <T> DeepBeanReader<T> forClass(Class<T> beanClass, Predicate<Class<?>> isBean) {
        return new DeepBeanReader<T>(new DeepObjectConverter(isBean));
    }
    
    private final DeepObjectConverter converter;
    private DeepBeanReader(DeepObjectConverter reader) {
        this.converter = reader;
    }
    
    @Override
    public PMap<String, Object> apply(T bean) {
        return EasyMap.of(transform(BeanPropertyEnumerator.enumerate(bean),
                                    Pair.<String, Object, Object>secondConverter(converter)));
    }
    
    private static class DeepObjectConverter implements Function<Object, Object> {

        private final Predicate<Class<?>> isBean;
        public DeepObjectConverter(Predicate<Class<?>> isBean) {
            this.isBean = isBean;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public Object apply(Object value) {
            if (value instanceof Collection) {
                return TreePVector.from(Lists.newArrayList(transform((Collection<Object>) value, this)));
            }
            
            if (value instanceof Map) {
                return EasyMap.extend(Maps.transformValues((Map<Object, Object>) value, this));
            }
            
            if (isBean.apply(value.getClass())) {
                return DeepBeanReader.read(value, isBean);
            }
            
            return value;
        }
    }
}
