package com.erkanerol.core;

import com.erkanerol.network.Peer;

import java.util.ArrayList;
import java.util.List;

public class Config {

    private final int port;
    private final String hostname;
    List<Peer> peerList;
    private final int poolSize;

    public Config(String hostname, int port, List<Peer> peerList, int poolSize) {
        this.hostname = hostname;
        this.port = port;
        this.peerList = peerList;
        this.poolSize = poolSize;
    }

    public int getPort() {
        return port;
    }

    public List<Peer> getPeerList() {
        return peerList;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPoolSize() {
        return poolSize;
    }
}
