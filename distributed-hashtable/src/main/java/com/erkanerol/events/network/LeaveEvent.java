package com.erkanerol.events.network;

import com.erkanerol.events.EventType;

public class LeaveEvent extends NetworkEvent {


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
