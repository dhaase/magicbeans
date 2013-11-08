package com.codepoetics.magicbeans.introspection;

import static com.google.common.collect.Iterables.transform;

import java.util.Map;

import com.codepoetics.magicbeans.tuples.Pair;
import com.google.common.base.Function;

public class BeanPropertyEnumerator<T> implements Function<T, Iterable<Pair<String, Object>>>{

    public static <T> Iterable<Pair<String, Object>> enumerate(T bean) {
        return BeanPropertyEnumerator.forClass(TheClass.of(bean)).apply(bean);
    }
    
    public static <T> BeanPropertyEnumerator<T> forClass(Class<T> beanClass) {
        return new BeanPropertyEnumerator<T>(PropertyAccessors.forClass(beanClass));
    }
    
    private final Map<String, Accessor<T, ?>> accessors;
    private BeanPropertyEnumerator(Map<String, Accessor<T, ?>> accessors) {
        this.accessors = accessors;
    }
    
    @Override
    public Iterable<Pair<String, Object>> apply(T bean) {
        return transform(accessors.entrySet(), new PropertyToPair(bean));
    }
    
    private class PropertyToPair implements Function<Map.Entry<String, Accessor<T, ?>>, Pair<String, Object>> {

        private final T bean;
        public PropertyToPair(T bean) {
            this.bean = bean;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public Pair<String, Object> apply(Map.Entry<String, Accessor<T, ?>> entry) {
            Accessor<T, Object> accessor = (Accessor<T, Object>) entry.getValue();
            return Pair.of(entry.getKey(), accessor.apply(bean));
        }
        
    }
}
