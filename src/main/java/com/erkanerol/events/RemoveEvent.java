package com.erkanerol.events;

public class RemoveEvent<K> extends MapEvent {

    private K key;

    public RemoveEvent(K key) {
        this.key = key;
    }

    @Override
    public MapEventType getType() {
        return MapEventType.REMOVE;
    }
}
