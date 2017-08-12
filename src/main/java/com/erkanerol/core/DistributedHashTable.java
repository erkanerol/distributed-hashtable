package com.erkanerol.core;

public interface DistributedHashTable<K,V> {

    void put(K key, V value);
    void putLocal(K key, V value);
    V get(K key);
    void remove(K key);
    void removeLocal(K key);
}
