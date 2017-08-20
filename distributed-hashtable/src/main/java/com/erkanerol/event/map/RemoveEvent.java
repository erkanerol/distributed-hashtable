package com.erkanerol.event.map;

import com.erkanerol.event.EventType;

public class RemoveEvent<K> extends TableEvent {

    private final String tableName;
    private final K key;

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
