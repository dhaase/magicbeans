package com.codepoetics.magicbeans.introspection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class Generics {

    private Generics() { }
    
    public static Class<?> classOf(Type type) {
        return (Class<?>) (type instanceof ParameterizedType ? ((ParameterizedType) type).getRawType() : type);
    }
    
    public static ParameterizedType parameterized(Type type) {
        return (ParameterizedType) type;
    }
    
    public static Type firstTypeParameter(Type type) {
        return parameterized(type).getActualTypeArguments()[0];
    }
    
    public static Type secondTypeParameter(Type type) {
        return parameterized(type).getActualTypeArguments()[1];
    }
}
