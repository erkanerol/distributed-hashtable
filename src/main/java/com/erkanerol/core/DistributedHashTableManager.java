package com.erkanerol.core;

import com.erkanerol.network.NetworkWorker;

import java.util.HashMap;
import java.util.Map;

public class DistributedHashTableManager {

    private final Config config;
    private final NetworkWorker networkWorker;
    public Map<String,DistributedHashTable> allMaps;

    public DistributedHashTableManager(Config config) {
        this.config = config;
        this.networkWorker = new NetworkWorker(config.getPort(),config.getHost());
        allMaps = new HashMap<>();
    }

    public void startUp(){
        //this.networkWorker.open();
    }

    public <K,V> DistributedHashTable<K,V> createDistributedHashTable(String mapName, Class<K> keyClass, Class<V> valueClass) {
        DistributedHashTableImpl<K, V> newMap = new DistributedHashTableImpl<K,V>();
        allMaps.put(mapName, newMap);
        return newMap;
    }
}
