package com.codepoetics.magicbeans.introspection;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;

public final class PropertyAccessors {

    private static final LoadingCache<Class<?>, Map<String, Accessor<?, ?>>> cache = CacheBuilder.newBuilder()
            .build(new CacheLoader<Class<?>, Map<String, Accessor<?, ?>>>() {
                @Override
                public Map<String, Accessor<?, ?>> load(Class<?> beanClass)
                        throws Exception {
                    BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
                    
                    ImmutableMap.Builder<String, Accessor<?, ?>> builder = ImmutableMap.builder();
                    for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
                        if (pd.getDisplayName().equals("class")) continue;
                        builder.put(pd.getDisplayName(), PropertyAccessor.<Object, Object>forDescriptor(pd));
                    }
                    
                    return builder.build();
                }
            });
    private PropertyAccessors() { }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> Map<String, Accessor<T, ?>> forClass(Class<T> beanClass) {
        try {
            return (Map) cache.get(beanClass);
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        }
    }
}
