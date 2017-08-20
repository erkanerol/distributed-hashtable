package com.erkanerol.event.network;

import com.erkanerol.event.EventType;

/**
 * event class that is used to send leave information to other nodes
 *
 */
public class LeaveEvent extends NetworkEvent {

    /**
     *
     * @param hostname the hostname of the node
     * @param port the port of the node
     */
    public LeaveEvent(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public EventType getType() {
        return EventType.LEAVE;
    }

    @Override
    public String toString() {
        return "LeaveEvent{" +
                "port=" + port +
                ", hostname='" + hostname + '\'' +
                '}';
    }
}
