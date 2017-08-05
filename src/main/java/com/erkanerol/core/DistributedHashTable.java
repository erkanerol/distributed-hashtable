package com.erkanerol.core;

public interface DistributedHashTable<K,V> {

    void put(K key, V value);
    V get(K key);
    void delete(K key);

}
