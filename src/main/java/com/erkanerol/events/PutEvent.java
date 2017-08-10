package com.erkanerol.events;

public class PutEvent<K,V> extends Event {

    private K key;
    private V value;


    public PutEvent(K key, V value) {
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
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
