package com.erkanerol.core;

import java.util.HashMap;
import java.util.Map;

public class DistributedHashTableImpl<K,V> implements  DistributedHashTable<K,V> {

    private final Map<K,V> internalMap;

    public DistributedHashTableImpl() {
        this.internalMap = new HashMap<>();
    }

    @Override
    public void put(K key, V value) {
        internalMap.put(key,value);
    }

    @Override
    public V get(K key) {
        return internalMap.get(key);
    }

    @Override
    public void remove(K key) {
        internalMap.remove(key);
    }
}
