package com.erkanerol.core;

import com.erkanerol.events.MapEventHandler;
import com.erkanerol.events.MapEventListener;
import com.erkanerol.events.map.MapEvent;
import com.erkanerol.events.map.PutEvent;
import com.erkanerol.events.map.RemoveEvent;
import com.erkanerol.network.NetworkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DistributedHashTableManager implements MapEventListener, MapEventHandler {

    private static Logger logger = LoggerFactory.getLogger(DistributedHashTableManager.class);

    private final NetworkManager networkManager;
    private Map<String,DistributedHashTable> allMaps;

    public DistributedHashTableManager(Config config) {
        this.networkManager = new NetworkManager(this,config);
        allMaps = new HashMap<>();
    }

    public void startUp(){
        this.networkManager.open();
        printMapState();
    }

    private void printMapState() {
        logger.info("Manager is up. Initial state:");
        for (Map.Entry entry: allMaps.entrySet()){
            logger.info("Key: {} Value: {}",entry.getKey(), entry.getValue());
        }
    }


    public void shutDown() {
        this.networkManager.close();
    }

    public synchronized  <K,V> DistributedHashTable<K,V> getDistributedHashTable(String mapName) {

        DistributedHashTable<K, V> newMap = (DistributedHashTable<K, V>) allMaps.get(mapName);

        if (newMap == null){
            newMap = creteNewMap(mapName);
        }

        return newMap;
    }

    private <K, V> DistributedHashTable<K, V> creteNewMap(String mapName) {
        DistributedHashTable<K, V> newMap = new DistributedHashTable<K,V>(mapName,this);
        allMaps.put(mapName, newMap);
        return newMap;
    }


    @Override
    public void processMapEvent(MapEvent mapEvent) {
        this.networkManager.propagate(mapEvent);
    }


    @Override
    public void handleMapEvent(MapEvent mapEvent) {
        if (mapEvent instanceof PutEvent){
            PutEvent putEvent = (PutEvent) mapEvent;
            DistributedHashTable<Object, Object> map = getDistributedHashTable(putEvent.getMapName());
            map.putLocal(putEvent.getKey(),putEvent.getValue());
            logger.info("Put event is handled",putEvent);
        } else if (mapEvent instanceof RemoveEvent){
            RemoveEvent removeEvent = (RemoveEvent) mapEvent;
            DistributedHashTable<Object, Object> map = getDistributedHashTable(removeEvent.getMapName());
            map.removeLocal(removeEvent.getKey());
            logger.info("Remove event is handled",removeEvent);
        }
    }

    public Map<String, DistributedHashTable> exportMaps() {
        return allMaps;
    }

    public void importMaps(Map<String, DistributedHashTable> importedMaps) {
        for (Map.Entry<String,DistributedHashTable> entry: importedMaps.entrySet()){
            allMaps.put(entry.getKey(),new DistributedHashTable(entry.getValue(),this));

        }
    }
}
