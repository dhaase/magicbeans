package com.codepoetics.magicbeans.arrays;

import java.util.Arrays;

public final class EasyArray {

    private EasyArray() { }
    
    @SuppressWarnings("unchecked")
    public static <T> T[] of(T...values) {
        return values;
    }
    
    public static <T> T[] replace(T[] array, int index, T value) {
        T[] newArray = Arrays.copyOf(array, array.length);
        newArray[index] = value;
        return newArray;
    }
}
