package com.erkanerol.events;

public class AttendEvent extends Event {

    private final int port;

    public AttendEvent(int port) {
        this.port = port;
    }

    @Override
    public EventType getType() {
        return EventType.ATTEND;
    }

    @Override
    public String toString() {
        return "AttendEvent{" +
                "port=" + port +
                '}';
    }
}
