package com.erkanerol.events;

public class PutEvent<K,V> extends MapEvent {

    private K key;
    private V value;


    public PutEvent(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public MapEventType getType() {
        return MapEventType.PUT;
    }
}
