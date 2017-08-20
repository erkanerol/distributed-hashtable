package com.erkanerol.event.network;

import com.erkanerol.event.Event;

/**
 * events for node activities in the network such as attend, leave etc.
 * */
public abstract class NetworkEvent extends Event {

    protected final int port;
    protected final String hostname;

    /**
     *
     * @param hostname the hostname of the node
     * @param port the port of the node
     */
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
