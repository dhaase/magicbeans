package com.codepoetics.magicbeans.lists;

import java.util.Collection;
import java.util.List;

import org.pcollections.PVector;
import org.pcollections.TreePVector;

import com.google.common.collect.ImmutableList;

public final class EasyList {

    private EasyList() { }
    
    @SafeVarargs
    public static <T> PVector<T> of(T...values) {
        return TreePVector.from(ImmutableList.copyOf(values));
    }
    
    public static <T> PVector<T> replace(List<T> list, int index, T value) {
        PVector<T> pvector = (list instanceof PVector) ? (PVector<T>) list : TreePVector.from(list);
        return pvector.with(index, value);
    }

    @SafeVarargs
    public static <T> PVector<T> extend(Collection<T> items, T...values) {
        return extend(items, of(values));
    }
    
    public static <T> PVector<T> extend(Collection<T> items, Collection<T> values) {
        PVector<T> pvector = (items instanceof PVector) ? (PVector<T>) items : TreePVector.from(items);
        return pvector.plusAll(values);
    }
}
