package com.erkanerol.events.network;

import com.erkanerol.events.Event;

public abstract class NetworkEvent extends Event{
    protected final int port;
    protected final String hostname;

    public NetworkEvent(String hostname, int port) {
        this.port = port;
        this.hostname = hostname;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }
}
