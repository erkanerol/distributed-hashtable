package com.erkanerol.event;

import com.erkanerol.event.map.TableEvent;

/**
 * handler that takes table events and changes the tables according to events
 */
public interface TableEventHandler {
    void handleTableEvent(TableEvent tableEvent);
}
