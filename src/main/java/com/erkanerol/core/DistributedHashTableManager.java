package com.erkanerol.core;

import com.erkanerol.events.CreateEvent;
import com.erkanerol.network.NetworkManager;

import java.util.HashMap;
import java.util.Map;

public class DistributedHashTableManager {

    private final NetworkManager networkManager;
    private Map<String,DistributedHashTable> allMaps;

    public DistributedHashTableManager(Config config) {
        this.networkManager = new NetworkManager(config);
        allMaps = new HashMap<>();
    }

    public void startUp(){
        this.networkManager.open();
    }

    public <K,V> DistributedHashTable<K,V> getDistributedHashTable(String mapName, Class<K> keyClass, Class<V> valueClass) {

        DistributedHashTableImpl<K, V> newMap = (DistributedHashTableImpl<K, V>) allMaps.get(mapName);

        if (newMap == null){
            newMap = new DistributedHashTableImpl<K,V>(this.networkManager);
            allMaps.put(mapName, newMap);
            this.networkManager.propagate(new CreateEvent<K,V>(mapName));
        }

        return newMap;
    }


}
