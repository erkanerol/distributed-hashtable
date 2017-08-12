package com.erkanerol.core;

import com.erkanerol.network.NetworkManager;

import java.util.HashMap;
import java.util.Map;

public class DistributedHashTableManager {

    private final NetworkManager networkManager;
    private Map<String,DistributedHashTable> allMaps;

    public DistributedHashTableManager(Config config) {
        this.networkManager = new NetworkManager(this,config);
        allMaps = new HashMap<>();
    }

    public void startUp(){
        this.networkManager.open();
    }

    public <K,V> DistributedHashTable<K,V> getDistributedHashTable(String mapName) {

        DistributedHashTableImpl<K, V> newMap = (DistributedHashTableImpl<K, V>) allMaps.get(mapName);

        if (newMap == null){
            newMap = creteNewMap(mapName);
        }

        return newMap;
    }

    public <K, V> DistributedHashTableImpl<K, V> creteNewMap(String mapName) {
        DistributedHashTableImpl<K, V> newMap = new DistributedHashTableImpl<K,V>(mapName,this.networkManager);
        allMaps.put(mapName, newMap);
        return newMap;
    }


}
