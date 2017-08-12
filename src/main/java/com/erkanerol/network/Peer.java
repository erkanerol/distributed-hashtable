package com.erkanerol.network;

public class Peer {

    private final String hostname;
    private final int port;

    public Peer(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "Peer{" +
                "hostname='" + hostname + '\'' +
                ", port=" + port +
                '}';
    }
}
