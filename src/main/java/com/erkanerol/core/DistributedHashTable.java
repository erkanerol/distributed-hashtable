package com.erkanerol.core;

import com.erkanerol.events.MapEventListener;
import com.erkanerol.events.map.PutEvent;
import com.erkanerol.events.map.RemoveEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DistributedHashTable<K,V> implements Serializable{

    private final String mapName;
    private final Map<K,V> internalMap;
    private transient final MapEventListener listener;

    public DistributedHashTable(String mapName, MapEventListener listener) {
        this.listener = listener;
        this.internalMap = new HashMap<>();
        this.mapName = mapName;
    }

    public DistributedHashTable(DistributedHashTable mapCopy, MapEventListener listener) {
        this.mapName = mapCopy.mapName;
        this.internalMap = mapCopy.internalMap;
        this.listener = listener;
    }

    public void put(K key, V value) {

        synchronized (this){
            internalMap.put(key,value);
            listener.processMapEvent(new PutEvent(this.mapName,key,value));
        }

    }

    public void putLocal(K key, V value) {

        synchronized (this){
            internalMap.put(key,value);
        }

    }


    public V get(K key) {
        return internalMap.get(key);
    }


    public void remove(K key) {

        synchronized (this){
            internalMap.remove(key);
            listener.processMapEvent(new RemoveEvent(this.mapName, key));
        }

    }

    public void removeLocal(K key) {
        synchronized (this){
            internalMap.remove(key);
        }
    }
}
