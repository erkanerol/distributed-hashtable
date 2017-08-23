package com.erkanerol.core;

import com.erkanerol.event.TableEventListener;
import com.erkanerol.event.map.PutEvent;
import com.erkanerol.event.map.RemoveEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * a simple wrapper for {@link java.util.Hashtable}
 * <p>
 * keeps a listener reference and calls it in the operations
 *
 * @author Erkan Erol
 */
public class DistributedHashTable<K, V> implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(DistributedHashTable.class);

    private final String tableName;
    private final Map<K, V> internalTable;
    private transient final TableEventListener listener;

    /**
     * constructor used in table creation
     *
     * @param tableName the name of the table
     * @param listener the listener for events
     */
    public DistributedHashTable(String tableName, TableEventListener listener) {
        this.listener = listener;
        this.internalTable = new HashMap<>();
        this.tableName = tableName;
    }

    /**
     * copy constructor used by nodes that attends the network later
     * to transfer the table as an object
     * @param mapCopy the object to copy
     * @param listener the listener of the new instance
     */
    public DistributedHashTable(DistributedHashTable mapCopy, TableEventListener listener) {
        this.tableName = mapCopy.tableName;
        this.internalTable = mapCopy.internalTable;
        this.listener = listener;
    }


    /**
     * puts an entry and calls the listener for event propagation
     * @param key the key of the entry
     * @param value the value of the entry
     */
    public void put(K key, V value) {
        logger.debug("PUT table:{} key:{} value:{}", tableName, key, value);
        synchronized (this) {
            internalTable.put(key, value);
            listener.processTableEvent(new PutEvent(this.tableName, key, value));
        }

    }

    /**
     * puts an entry without propagating an event in the network.
     * used in processing an event received from another peer
     * @param key the key of the entry
     * @param value the value of the entry
     */
    protected void putLocal(K key, V value) {
        logger.debug("PUT LOCAL table:{} key:{} value:{}", tableName, key, value);
        synchronized (this) {
            internalTable.put(key, value);
        }

    }

    /**
     * gets the entry from table
     * @param key the key of the element
     * @return
     */
    public V get(K key) {
        logger.debug("GET table:{} key:{} value:{}", tableName, key);
        return internalTable.get(key);
    }


    /**
     * removes an entry and calls the listener for event propagation
     * @param key
     */
    public void remove(K key) {
        logger.debug("REMOVE table:{} key:{}", tableName, key);
        synchronized (this) {
            internalTable.remove(key);
            listener.processTableEvent(new RemoveEvent(this.tableName, key));
        }

    }

    /**
     * removes an entry without propagating an event in the network.
     * used in processing an event received from another peer
     * @param key
     */
    protected void removeLocal(K key) {
        logger.debug("REMOVE LOCAL table:{} key:{}", tableName, key);
        synchronized (this) {
            internalTable.remove(key);
        }
    }

    /**
     * prints the content of the table with logger in trace level
     */
    protected void printContent() {
        for (Map.Entry entry : internalTable.entrySet()) {
            logger.trace("Table:{},  Key:{}  Value:{}" + tableName, entry.getKey(), entry.getValue());
        }
    }
}
