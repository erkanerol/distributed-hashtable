package com.erkanerol.core;

import com.erkanerol.network.Peer;

import java.util.ArrayList;
import java.util.List;

/**
 * contains configs for {@link com.erkanerol.core.DistributedHashTableManager}
 *
 * @author Erkan Erol
 */
public class Config {
	
    public static final int DEFAULT_PORT = 9878;
    public static final String DEFAULT_HOSTNAME = "localhost";
    public static final int DEFAULT_POOL_SIZE = 10;

    private String hostname;
    private int port;
    private List<Peer> peerList;
    private int poolSize;

    /**
    * contsruct a config with default values
    */
   public Config() {
	   this.port = DEFAULT_PORT;
       this.hostname = DEFAULT_HOSTNAME;
       this.peerList = new ArrayList<>();
       this.poolSize = DEFAULT_POOL_SIZE;
   }
   
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

    public void addPeer(Peer peer){
    	this.peerList.add(peer);
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

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}
    
}
