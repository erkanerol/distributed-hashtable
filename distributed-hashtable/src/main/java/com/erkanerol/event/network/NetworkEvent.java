package com.erkanerol.event.network;

import com.erkanerol.event.Event;

public abstract class NetworkEvent extends Event {
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
