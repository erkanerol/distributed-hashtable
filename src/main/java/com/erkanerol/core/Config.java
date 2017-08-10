package com.erkanerol.core;

import com.erkanerol.network.Peer;

import java.util.List;

public class Config {

    int port;
    List<Peer> peerList;

    public Config(int port) {
        this.port = port;
    }

    public Config(int port, List<Peer> peerList) {
        this.port = port;
        this.peerList = peerList;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<Peer> getPeerList() {
        return peerList;
    }

    public void setPeerList(List<Peer> peerList) {
        this.peerList = peerList;
    }
}
