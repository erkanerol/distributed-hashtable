package com.erkanerol.events;

public class CreateEvent<K,V> extends Event {


    private final String mapName;

    public <K, V> CreateEvent(String mapName) {
        this.mapName = mapName;
    }

    @Override
    public EventType getType() {
        return EventType.CREATE;
    }

    @Override
    public String toString() {
        return "CreateEvent{" +
                "mapName='" + mapName + '\'' +
                '}';
    }

    public String getMapName() {
        return mapName;
    }
}
