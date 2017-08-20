package com.erkanerol.event.map;

import com.erkanerol.event.EventType;

public class PutEvent<K, V> extends TableEvent {

    private final String tableName;
    private final K key;
    private final V value;

    public PutEvent(String tableName, K key, V value) {
        this.tableName = tableName;
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
                "tableName='" + tableName + '\'' +
                ", key=" + key +
                ", value=" + value +
                '}';
    }


    public String getTableName() {
        return tableName;
    }


    public K getKey() {
        return key;
    }


    public V getValue() {
        return value;
    }

}
