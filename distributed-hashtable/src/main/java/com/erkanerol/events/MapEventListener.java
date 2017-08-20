package com.erkanerol.events;

import com.erkanerol.events.map.MapEvent;

public interface MapEventListener {
    void processMapEvent(MapEvent mapEvent);
}
