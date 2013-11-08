package com.codepoetics.magicbeans.lenses;

import com.codepoetics.magicbeans.arrays.EasyArray;

public class ArrayLens<T> implements Lens<T[], T> {
    
    public static <T> ArrayLens<T> atIndex(int index) {
        return new ArrayLens<T>(index);
    }
    
    private final int index;
    
    private ArrayLens(int index) {
        this.index = index;
    }
    @Override
    public Lensed<T[], T> apply(T[] array) {
        return new ArrayLensed(array);
    }
    
    private class ArrayLensed implements Lensed<T[], T> {

        private final T[] array;
        
        public ArrayLensed(T[] array) {
            this.array = array;
        }

        @Override
        public T get() {
            return array[index];
        }

        @Override
        public T[] apply(T value) {
            return EasyArray.replace(array, index, value);
        }
        
    }

}
