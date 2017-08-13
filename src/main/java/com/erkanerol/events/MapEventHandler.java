package com.erkanerol.events;

import com.erkanerol.events.map.MapEvent;

public interface MapEventHandler {
    void handleMapEvent(MapEvent mapEvent);
}
