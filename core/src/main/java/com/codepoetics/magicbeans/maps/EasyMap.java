package com.codepoetics.magicbeans.maps;

import java.util.Collections;
import java.util.Map;

import org.pcollections.HashTreePMap;
import org.pcollections.PMap;

import com.codepoetics.magicbeans.tuples.Pair;
import com.codepoetics.magicbeans.tuples.Pairs;
import com.google.common.collect.ImmutableMap;

public final class EasyMap {

    private EasyMap() { }
    
    public static <K, V> PMap<K, V> of(Object...properties) {
        return of(Pairs.<K, V>fromVarArgs(properties));
    }
    
    public static PMap<String, Object> properties(Object...properties) {
        return of(properties);
    }
    
    public static <K, V> PMap<K, V> of(Iterable<Pair<K, V>> properties) {
        Map<K, V> emptyMap = Collections.emptyMap();
        return extend(emptyMap, properties);
    }
    
    public static <K, V> PMap<K, V> extend(Map<K, V> base, Object...properties) {
        return extend(base, Pairs.<K, V>fromVarArgs(properties));
    }
    
    public static <K, V> PMap<K, V> extend(Map<K, V> base, Iterable<Pair<K, V>> properties) {
        ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
        for (Pair<K, V> property : properties) {
            builder.put(property.first, property.second);
        }
        return extend(base, builder.build());
    }
    
    public static <K, V> PMap<K, V> extend(Map<K, V> base, Map<? extends K, ? extends V> merge) {
        PMap<K, V> baseP = (base instanceof PMap) ? (PMap<K, V>) base : HashTreePMap.from(base);
        return baseP.plusAll(merge);
    }
 }
