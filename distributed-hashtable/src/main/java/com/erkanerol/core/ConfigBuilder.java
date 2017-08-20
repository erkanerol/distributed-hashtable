package com.erkanerol.core;

import com.erkanerol.network.Peer;

import java.util.ArrayList;
import java.util.List;

public class ConfigBuilder {

    private int port = 9878;
    private String hostname = "localhost";
    private List<Peer> peerList = new ArrayList<>();
    private int poolSize = 10;


    public ConfigBuilder(){

    }

    public ConfigBuilder setPort(int port){
        this.port = port;
        return this;
    }

    public ConfigBuilder setHostName(String hostname){
        this.hostname = hostname;
        return this;
    }

    public ConfigBuilder addPeer(Peer peer){
        this.peerList.add(peer);
        return this;
    }

    public ConfigBuilder setPoolSize(int poolSize){
        this.poolSize = poolSize;
        return this;
    }


    public Config createConfig(){
        return new Config(hostname,port,peerList,poolSize);
    }

    public static ConfigBuilder builder(){
        return new ConfigBuilder();
    }
}
