package com.erkanerol.core;


public class DistributedHashTableManagerFactory {


    public static DistributedHashTableManager createNewInstance(Config config) {
        DistributedHashTableManager manager = new DistributedHashTableManager(config) ;
        manager.startUp();
        return manager;
    }

}
