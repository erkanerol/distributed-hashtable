package com.erkanerol.event.network;

import com.erkanerol.event.EventType;

/**
 * event class that is used to send attendance information to other nodes and get the current state of the manager
 *
 */
public class AttendEvent extends NetworkEvent {

    private final boolean initialStateRequest;

    /**
     *
     * @param hostname the hostname of the node
     * @param port the port of the node
     * @param initialStateRequest whether or not the node request the initial state of manager
     */
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
