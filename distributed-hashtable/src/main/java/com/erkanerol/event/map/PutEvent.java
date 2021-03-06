package com.erkanerol.event.map;

import com.erkanerol.event.EventType;

/**
 * event class to transfer put operation information to other nodes
 * */
public class PutEvent<K, V> extends TableEvent {

    private final String tableName;
    private final K key;
    private final V value;

    /**
     * @param tableName the name of table from which an entry is removed
     * @param key the key of the entry removed
     * @param value
     */
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
