package com.codepoetics.magicbeans.introspection;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import com.google.common.base.Optional;

final class PropertySetter {

    private PropertySetter() { }
    
    public static <T, P> Optional<Setter<T, P>> forDescriptor(PropertyDescriptor pd) {
        if (pd.getWriteMethod() == null) {
            if (Collection.class.isAssignableFrom(pd.getReadMethod().getReturnType())) {
                return Optional.<Setter<T, P>>of(new CollectionPropertySetter<T, P>(pd.getReadMethod()));
            }
            if (Map.class.isAssignableFrom(pd.getReadMethod().getReturnType())) {
                return Optional.<Setter<T, P>>of(new MapPropertySetter<T, P>(pd.getReadMethod()));
            }
            return Optional.absent();
        }
        return Optional.<Setter<T, P>>of(new MethodPropertySetter<T, P>(pd.getWriteMethod()));
    }
    
    
    private static class CollectionPropertySetter<T, P> implements Setter<T, P> {
        private final Method readMethod;
        public CollectionPropertySetter(Method readMethod) {
            this.readMethod = readMethod;
        }
        @SuppressWarnings({ "rawtypes", "unchecked" })
        @Override
        public void set(T instance, P value) {
            try {
                Collection targetList = (Collection) readMethod.invoke(instance);
                targetList.clear();
                Collection newList = (Collection) value;
                targetList.addAll(newList);
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    private static class MapPropertySetter<T, P> implements Setter<T, P> {
        private final Method readMethod;
        public MapPropertySetter(Method readMethod) {
            this.readMethod = readMethod;
        }
        @SuppressWarnings({ "rawtypes", "unchecked" })
        @Override
        public void set(T instance, P value) {
            try {
                Map targetMap = (Map) readMethod.invoke(instance);
                targetMap.clear();
                Map newMap = (Map) value;
                targetMap.putAll(newMap);
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    private static class MethodPropertySetter<T, P> implements Setter<T, P> {
        private final Method writeMethod;
        public MethodPropertySetter(Method writeMethod) {
            this.writeMethod = writeMethod;
        }
        @Override
        public void set(T instance, P value) {
            try {
                if (value == null && writeMethod.getParameterTypes()[0].isPrimitive()) {
                    return;
                }
                writeMethod.invoke(instance, value);
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
}
