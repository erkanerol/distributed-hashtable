package com.erkanerol.core;

import com.erkanerol.event.TableEventListener;
import com.erkanerol.event.map.PutEvent;
import com.erkanerol.event.map.RemoveEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

public class DistributedHashTable<K,V> implements Serializable{

    private static Logger logger = LoggerFactory.getLogger(DistributedHashTable.class);

    private final String tableName;
    private final Map<K,V> internalTable;
    private transient final TableEventListener listener;

    public DistributedHashTable(String tableName, TableEventListener listener) {
        this.listener = listener;
        this.internalTable = new Hashtable<>();
        this.tableName = tableName;
    }

    public DistributedHashTable(DistributedHashTable mapCopy, TableEventListener listener) {
        this.tableName = mapCopy.tableName;
        this.internalTable = mapCopy.internalTable;
        this.listener = listener;
    }

    public void put(K key, V value) {
        logger.debug("PUT table:{} key:{} value:{}",tableName,key,value);
        synchronized (this){
            internalTable.put(key,value);
            listener.processTableEvent(new PutEvent(this.tableName,key,value));
        }

    }

    protected void putLocal(K key, V value) {
        logger.debug("PUT LOCAL table:{} key:{} value:{}",tableName,key,value);
        synchronized (this){
            internalTable.put(key,value);
        }

    }


    public V get(K key) {
        logger.debug("GET table:{} key:{} value:{}",tableName,key);
        return internalTable.get(key);
    }


    public void remove(K key) {
        logger.debug("REMOVE table:{} key:{} value:{}",tableName,key);
        synchronized (this){
            internalTable.remove(key);
            listener.processTableEvent(new RemoveEvent(this.tableName, key));
        }

    }

    protected void removeLocal(K key) {
        logger.debug("REMOVE LOCAL table:{} key:{} value:{}",tableName,key);
        synchronized (this){
            internalTable.remove(key);
        }
    }

    protected void printContent(){
        for (Map.Entry entry: internalTable.entrySet()){
            logger.trace("Table:{},  Key:{}  Value:{}"+tableName,entry.getKey(),entry.getValue());
        }
    }
}
