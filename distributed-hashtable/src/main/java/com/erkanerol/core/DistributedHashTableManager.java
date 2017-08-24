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

/**
 * an organizer class for managing multiple distributed hash table with same network components.
 *
 *
 * @author Erkan Erol
 */
public class DistributedHashTableManager implements TableEventListener, TableEventHandler {

    private static Logger logger = LoggerFactory.getLogger(DistributedHashTableManager.class);

    private final NetworkManager networkManager;
    private Map<String, DistributedHashTable> allTables;

    /**
     * creates a network manager and creates an empty map for all tables
     * @param config the configuration of the manager
     */
    public DistributedHashTableManager(Config config) {
        this.networkManager = new NetworkManager(this, config);
        this.allTables = new Hashtable<>();
    }


    /**
     * opens the network manager
     */
    public void startUp() {
        logger.debug("Distributed Hash Table is starting...");
        networkManager.open();
        printInitialState();
    }

    /**
     * closes the network manager
     */
    public void shutDown() {
        logger.debug("Distributed Hash Table is shutting down...");
        this.networkManager.close();
    }

    /**
     * returns the table related to given name.
     * if there is no table related to given table name, creates a new one and returns it
     *
     * @param tableName the name of the table
     *
     * @return the table related to given table name
     *
     */
    public synchronized <K, V> DistributedHashTable<K, V> getDistributedHashTable(String tableName) {

        DistributedHashTable<K, V> newTable = (DistributedHashTable<K, V>) allTables.get(tableName);

        if (newTable == null) {
            newTable = creteNewTable(tableName);
        }

        return newTable;
    }

    /**
     * creates a new distributed hash table and put it into all tables with given map name as key
     * @param tableName the name of distributed hash table
     * @return the created distributed hash table
     */
    private <K, V> DistributedHashTable<K, V> creteNewTable(String tableName) {
        DistributedHashTable<K, V> newMap = new DistributedHashTable<K, V>(tableName, this);
        allTables.put(tableName, newMap);
        return newMap;
    }


    /**
     * process an event (put,remove etc.) occurred in one of the table.
     * @param tableEvent
     */
    @Override
    public void processTableEvent(TableEvent tableEvent) {
        this.networkManager.propagate(tableEvent);
    }


    /**
     * handles an event came from network
     *
     * the synchronization are done by each table
     * @param tableEvent
     */
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

    /**
     * exports tables for transferring all tables together
     * @return the all tables
     */
    public Map<String, DistributedHashTable> exportTables() {
        return allTables;
    }

    /**
     * imports tables for transferring all tables together
     * @param importedMaps the maps to be imported
     */
    public void importTables(Map<String, DistributedHashTable> importedMaps) {
        for (Map.Entry<String, DistributedHashTable> entry : importedMaps.entrySet()) {
            allTables.put(entry.getKey(), new DistributedHashTable(entry.getValue(), this));

        }
    }


    /**
     * prints the state of manager with logger
     */
    private void printInitialState() {
        if (logger.isDebugEnabled()) {
            logger.debug("Manager is up. Initial state:");

            for (Map.Entry entry : allTables.entrySet()) {
                logger.debug("Table: {}", entry.getKey());

                if (logger.isTraceEnabled()) {
                    DistributedHashTable table = (DistributedHashTable) entry.getValue();
                    table.printContent();
                }

            }
        }
    }
}
