package com.erkanerol.events;

public class CreateEvent<K,V> extends MapEvent {


    private final String mapName;

    public <K, V> CreateEvent(String mapName) {
        this.mapName = mapName;
    }

    @Override
    public MapEventType getType() {
        return MapEventType.CREATE;
    }
}
