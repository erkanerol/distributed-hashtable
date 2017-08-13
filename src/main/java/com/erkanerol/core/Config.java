package com.erkanerol.core;

import com.erkanerol.network.Peer;

import java.util.ArrayList;
import java.util.List;

public class Config {

    private final int port;
    private final String hostname;
    List<Peer> peerList;

    public Config(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        this.peerList = new ArrayList<>();
    }

    public Config(String hostname, int port, List<Peer> peerList) {
        this.hostname = hostname;
        this.port = port;
        this.peerList = peerList;
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
}
