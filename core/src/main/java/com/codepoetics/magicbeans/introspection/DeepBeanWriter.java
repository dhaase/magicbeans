package com.codepoetics.magicbeans.introspection;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

public class DeepBeanWriter<T> implements BeanWriter<T> {

    public static final Predicate<Class<?>> nonScalar = new NonScalar();
    
    public static <T> T write(Class<T> beanClass, Map<String, Object> data) {
        return write(beanClass, data, nonScalar);
    };
    
    public static <T> T write(Class<T> beanClass, Map<String, Object> data, Predicate<Class<?>> isBean) {
        return forClass(beanClass, isBean).apply(data);
    }
    
    public static <T> DeepBeanWriter<T> forClass(Class<T> beanClass) {
        return forClass(beanClass, nonScalar);
    }
    
    public static <T> DeepBeanWriter<T> forClass(Class<T> beanClass, Predicate<Class<?>> isBean) {
        return new DeepBeanWriter<T>(ShallowBeanWriter.forClass(beanClass),
                new DeepPropertyConverter<T>(PropertyAccessors.forClass(beanClass), isBean));
    }
    
    private final BeanWriter<T> shallowWriter;
    private final DeepPropertyConverter<T> converter;
    
    private DeepBeanWriter(BeanWriter<T> shallowWriter, DeepPropertyConverter<T> converter) {
        this.shallowWriter = shallowWriter;
        this.converter = converter;
    }
    
    @Override
    public T apply(Map<String, Object> properties) {
        Map<String, Object> converted = Maps.transformEntries(properties, converter);
        return shallowWriter.apply(converted);
    }
    
    private static class DeepPropertyConverter<T> implements Maps.EntryTransformer<String, Object, Object> {

        private final Map<String, Accessor<T, ?>> accessors;
        private final Predicate<Class<?>> isBean;
        
        public DeepPropertyConverter(Map<String, Accessor<T, ?>> accessors, Predicate<Class<?>> isBean) {
            this.accessors = accessors;
            this.isBean = isBean;
        }
        
        @Override
        public Object transformEntry(String propertyName, Object data) {
            return toType(accessors.get(propertyName).type()).apply(data);
        }
         
        private Function<Object, Object> toType(final Type type) {
            return new Function<Object, Object>() {
                @SuppressWarnings("unchecked")
                @Override
                public Object apply(Object data) {
                    Class<?> rawClass = Generics.classOf(type);
                    
                    if (List.class.isAssignableFrom(rawClass)) {
                        return newArrayList(transform((Iterable<Object>) data, toType(Generics.firstTypeParameter(type))));
                    }
                    
                    if (Map.class.isAssignableFrom(rawClass)) {
                        return Maps.transformValues((Map<Object, Object>) data, toType(Generics.secondTypeParameter(type)));
                    }
                    
                    if (isBean.apply(rawClass)) {
                        return DeepBeanWriter.write(rawClass, (Map<String, Object>) data, isBean);
                    }
                    
                    return data;
                }
            };
        }
    }
}
