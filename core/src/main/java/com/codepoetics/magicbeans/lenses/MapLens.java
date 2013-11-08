package com.codepoetics.magicbeans.lenses;

import java.util.Map;

import com.codepoetics.magicbeans.maps.EasyMap;

public class MapLens<K, V> implements Lens<Map<K, V>, V>{

    public static <K, V> MapLens<K, V> atKey(K key) {
        return new MapLens<K, V>(key);
    }
    
    private final K key;
    private MapLens(K key) {
        this.key = key;
    }
    
    @Override
    public Lensed<Map<K, V>, V> apply(Map<K, V> map) {
        return new MapLensed(map);
    }

    private class MapLensed implements Lensed<Map<K, V>, V> {

        private final Map<K, V> map;
        
        public MapLensed(Map<K, V> map) {
            this.map = map;
        }

        @Override
        public V get() {
            return map.get(key);
        }

        @Override
        public Map<K, V> apply(V value) {
            return EasyMap.extend(map, key, value);
        }
    }
}
