package com.erkanerol.event;

import com.erkanerol.event.map.TableEvent;

/**
 * listener that process the table events came from other nodes via network
 */
public interface TableEventListener {
    void processTableEvent(TableEvent tableEvent);
}
