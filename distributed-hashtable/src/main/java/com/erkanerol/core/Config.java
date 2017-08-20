package com.erkanerol.core;

import com.erkanerol.network.Peer;

import java.util.List;

/**
 * contains configs for {@link com.erkanerol.core.DistributedHashTableManager}
 *
 * @author Erkan Erol
 */
public class Config {

    private final String hostname;
    private final int port;
    private List<Peer> peerList;
    private final int poolSize;

    /**
     *
     * @param hostname hostname of the node in P2P network
     * @param port server port of the node in P2P network
     * @param peerList initial peer list using in startup
     * @param poolSize the thread pool size for network message processing
     *
     */
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
