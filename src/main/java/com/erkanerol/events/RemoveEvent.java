package com.erkanerol.events;

public class RemoveEvent<K> extends Event {

    private K key;

    public RemoveEvent(K key) {
        this.key = key;
    }

    @Override
    public EventType getType() {
        return EventType.REMOVE;
    }

    @Override
    public String toString() {
        return "RemoveEvent{" +
                "key=" + key +
                '}';
    }
}
