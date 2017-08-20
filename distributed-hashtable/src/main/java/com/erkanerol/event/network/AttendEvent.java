package com.erkanerol.event.network;

import com.erkanerol.event.EventType;

public class AttendEvent extends NetworkEvent {

    private final boolean initialStateRequest;

    public AttendEvent(String hostname, int port, boolean initialStateRequest) {
        super(hostname, port);
        this.initialStateRequest = initialStateRequest;
    }

    @Override
    public EventType getType() {
        return EventType.ATTEND;
    }


    @Override
    public String toString() {
        return "AttendEvent{" +
                "port=" + port +
                ", hostname='" + hostname + '\'' +
                '}';
    }


    public boolean isInitialStateRequest() {
        return initialStateRequest;
    }
}
