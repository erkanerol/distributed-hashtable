package com.erkanerol.core;

import com.erkanerol.event.TableEventHandler;
import com.erkanerol.event.TableEventListener;
import com.erkanerol.event.map.PutEvent;
import com.erkanerol.event.map.RemoveEvent;
import com.erkanerol.event.map.TableEvent;
import com.erkanerol.network.NetworkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;
import java.util.Map;

public class DistributedHashTableManager implements TableEventListener, TableEventHandler {

    private static Logger logger = LoggerFactory.getLogger(DistributedHashTableManager.class);

    private final NetworkManager networkManager;
    private Map<String, DistributedHashTable> allMaps;

    public DistributedHashTableManager(Config config) {
        this.networkManager = new NetworkManager(this, config);
        this.allMaps = new Hashtable<>();
    }

    public void startUp() {
        logger.debug("Distributed Hash Table is starting...");
        networkManager.open();
        printInitialState();
    }

    public void shutDown() {
        logger.debug("Distributed Hash Table is shutting down...");
        this.networkManager.close();
    }

    public synchronized <K, V> DistributedHashTable<K, V> getDistributedHashTable(String mapName) {

        DistributedHashTable<K, V> newMap = (DistributedHashTable<K, V>) allMaps.get(mapName);

        if (newMap == null) {
            newMap = creteNewTable(mapName);
        }

        return newMap;
    }

    private <K, V> DistributedHashTable<K, V> creteNewTable(String mapName) {
        DistributedHashTable<K, V> newMap = new DistributedHashTable<K, V>(mapName, this);
        allMaps.put(mapName, newMap);
        return newMap;
    }


    @Override
    public void processTableEvent(TableEvent tableEvent) {
        this.networkManager.propagate(tableEvent);
    }


    @Override
    public void handleTableEvent(TableEvent tableEvent) {
        if (tableEvent instanceof PutEvent) {
            PutEvent putEvent = (PutEvent) tableEvent;
            DistributedHashTable<Object, Object> map = getDistributedHashTable(putEvent.getTableName());
            map.putLocal(putEvent.getKey(), putEvent.getValue());
            logger.info("Put event is handled", putEvent);
        } else if (tableEvent instanceof RemoveEvent) {
            RemoveEvent removeEvent = (RemoveEvent) tableEvent;
            DistributedHashTable<Object, Object> map = getDistributedHashTable(removeEvent.getTableName());
            map.removeLocal(removeEvent.getKey());
            logger.info("Remove event is handled", removeEvent);
        }
    }

    public Map<String, DistributedHashTable> exportTables() {
        return allMaps;
    }

    public void importTables(Map<String, DistributedHashTable> importedMaps) {
        for (Map.Entry<String, DistributedHashTable> entry : importedMaps.entrySet()) {
            allMaps.put(entry.getKey(), new DistributedHashTable(entry.getValue(), this));

        }
    }


    private void printInitialState() {
        if (logger.isDebugEnabled()) {
            logger.debug("Manager is up. Initial state:");

            for (Map.Entry entry : allMaps.entrySet()) {
                logger.debug("Table: {}", entry.getKey());

                if (logger.isTraceEnabled()) {
                    DistributedHashTable table = (DistributedHashTable) entry.getValue();
                    table.printContent();
                }

            }
        }
    }
}
