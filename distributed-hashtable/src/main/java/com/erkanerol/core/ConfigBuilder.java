package com.erkanerol.core;

import com.erkanerol.network.Peer;

import java.util.ArrayList;
import java.util.List;

/**
 * builders for {@link com.erkanerol.core.Config}
 *
 * @author Erkan Erol
 */

public class ConfigBuilder {

    public static final int DEFAULT_PORT = 9878;
    public static final String DEFAULT_HOSTNAME = "localhost";
    public static final int DEFAULT_POOL_SIZE = 10;

    private int port;
    private String hostname;
    private List<Peer> peerList;
    private int poolSize = 10;


    public ConfigBuilder() {
        this.port = DEFAULT_PORT;
        this.hostname = DEFAULT_HOSTNAME;
        this.peerList = new ArrayList<>();
        this.poolSize = DEFAULT_POOL_SIZE;
    }

    public ConfigBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public ConfigBuilder setHostName(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public ConfigBuilder addPeer(Peer peer) {
        this.peerList.add(peer);
        return this;
    }

    public ConfigBuilder setPoolSize(int poolSize) {
        this.poolSize = poolSize;
        return this;
    }


    public Config createConfig() {
        return new Config(hostname, port, peerList, poolSize);
    }

    public static ConfigBuilder builder() {
        return new ConfigBuilder();
    }
}
