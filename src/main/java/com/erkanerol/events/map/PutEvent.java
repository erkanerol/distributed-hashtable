package com.erkanerol.events.map;

import com.erkanerol.events.EventType;

public class PutEvent<K,V> extends MapEvent {

    private final String mapName;
    private final K key;
    private final V value;

    public PutEvent(String mapName, K key, V value) {
        this.mapName = mapName;
        this.key = key;
        this.value = value;
    }

    @Override
    public EventType getType() {
        return EventType.PUT;
    }

    @Override
    public String toString() {
        return "PutEvent{" +
                "mapName='" + mapName + '\'' +
                ", key=" + key +
                ", value=" + value +
                '}';
    }


    public String getMapName() {
        return mapName;
    }


    public K getKey() {
        return key;
    }


    public V getValue() {
        return value;
    }

}
