package com.erkanerol.event.map;

import com.erkanerol.event.EventType;

/**
 * event class to transfer remove operation information to other nodes
 * */
public class RemoveEvent<K> extends TableEvent {

    private final String tableName;
    private final K key;

    /**
     * @param tableName the name of table from which an entry is removed
     * @param key the key of the entry removed
     */
    public RemoveEvent(String tableName, K key) {
        this.tableName = tableName;
        this.key = key;
    }

    @Override
    public EventType getType() {
        return EventType.REMOVE;
    }

    @Override
    public String toString() {
        return "RemoveEvent{" +
                "tableName='" + tableName + '\'' +
                ", key=" + key +
                '}';
    }

    public String getTableName() {
        return tableName;
    }

    public K getKey() {
        return key;
    }

}
