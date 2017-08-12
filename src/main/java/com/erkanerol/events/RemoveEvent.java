package com.erkanerol.events;

public class RemoveEvent<K> extends Event {

    private final String mapName;
    private final K key;

    public RemoveEvent(String mapName, K key) {
        this.mapName = mapName;
        this.key = key;
    }

    @Override
    public EventType getType() {
        return EventType.REMOVE;
    }

    @Override
    public String toString() {
        return "RemoveEvent{" +
                "mapName='" + mapName + '\'' +
                ", key=" + key +
                '}';
    }

    public String getMapName() {
        return mapName;
    }

    public K getKey() {
        return key;
    }

}
