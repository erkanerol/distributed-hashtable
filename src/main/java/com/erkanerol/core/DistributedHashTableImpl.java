package com.erkanerol.core;

import com.erkanerol.events.PutEvent;
import com.erkanerol.events.RemoveEvent;
import com.erkanerol.network.NetworkManager;

import java.util.HashMap;
import java.util.Map;

public class DistributedHashTableImpl<K,V> implements  DistributedHashTable<K,V> {

    private final String mapName;
    private final Map<K,V> internalMap;
    private final NetworkManager networkManager;

    private final Object lock;

    public DistributedHashTableImpl(String mapName, NetworkManager networkManager) {
        this.networkManager = networkManager;
        this.internalMap = new HashMap<>();
        this.mapName = mapName;
        lock = new Object();
    }

    @Override
    public void put(K key, V value) {

        synchronized (lock){
            internalMap.put(key,value);
            networkManager.propagate(new PutEvent(this.mapName,key,value));
        }

    }

    @Override
    public void putLocal(K key, V value) {

        synchronized (lock){
            internalMap.put(key,value);
        }

    }

    @Override
    public V get(K key) {
        return internalMap.get(key);
    }

    @Override
    public void remove(K key) {

        synchronized (lock){
            internalMap.remove(key);
            networkManager.propagate(new RemoveEvent(this.mapName, key));
        }

    }

    @Override
    public void removeLocal(K key) {
        synchronized (lock){
            internalMap.remove(key);
        }
    }
}
