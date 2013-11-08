package com.codepoetics.magicbeans.properties;

import java.util.Map;

import com.codepoetics.magicbeans.introspection.PropertyAccessors;
import com.codepoetics.magicbeans.introspection.PropertyMapIso;
import com.codepoetics.magicbeans.lenses.Lens;
import com.codepoetics.magicbeans.lenses.MapLens;
import com.codepoetics.magicbeans.morphisms.Isomorphism;
import com.codepoetics.magicbeans.morphisms.Isomorphisms;
import com.google.common.base.Preconditions;

public final class PropertyLens {

    private PropertyLens() { }
    
    public static <T, P> Lens<T, P> forProperty(String propertyName, Class<T> beanClass) {
        Preconditions.checkArgument(PropertyAccessors.forClass(beanClass).containsKey(propertyName),
                "Bean class " + beanClass + " has no property " + propertyName);
        
        Isomorphism<T, Map<String, P>> iso = Isomorphisms.cast(PropertyMapIso.shallow(beanClass));
        MapLens<String, P> lens = MapLens.atKey(propertyName);
        
        return Isomorphisms.wrap(lens, iso);
    }
}
