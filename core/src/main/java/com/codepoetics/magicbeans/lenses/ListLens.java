package com.codepoetics.magicbeans.lenses;

import java.util.List;

import com.codepoetics.magicbeans.lists.EasyList;

public class ListLens<T> implements Lens<List<T>, T>{
    
    public static <T> ListLens<T> atIndex(int index) {
        return new ListLens<T>(index);
    }
    
    private final int index;
    
    private ListLens(int index) {
        this.index = index;
    }

    @Override
    public Lensed<List<T>, T> apply(List<T> list) {
        return new ListLensed(list);
    }
    
    private class ListLensed implements Lensed<List<T>, T> {

        private final List<T> list;

        public ListLensed(List<T> list) {
            this.list = list;
        }

        @Override
        public T get() {
            return list.get(index);
        }

        @Override
        public List<T> apply(T value) {
            return EasyList.replace(list, index, value);
        }
    }

}
