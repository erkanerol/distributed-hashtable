package com.erkanerol.network;

/**
 * a class used to represent an instance of the manager in the network
 */
public class Peer {

    private final String hostname;
    private final int port;

    /**
     *
     * @param hostname hostname that the peer listens
     * @param port port that the peer listens
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Peer peer = (Peer) o;

        if (port != peer.port) return false;
        return hostname != null ? hostname.equals(peer.hostname) : peer.hostname == null;
    }

    @Override
    public int hashCode() {
        int result = hostname != null ? hostname.hashCode() : 0;
        result = 31 * result + port;
        return result;
    }
}
