package com.erkanerol.core;

public class Config {

    private final String host;
    private final int port;


    public Config(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
