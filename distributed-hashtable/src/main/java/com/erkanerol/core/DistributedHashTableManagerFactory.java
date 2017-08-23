package com.erkanerol.core;


public class DistributedHashTableManagerFactory {

    /**
     * creates a new {@link DistributedHashTableManager} with default configs and starts it
     * */
    public static DistributedHashTableManager createNewInstance() {
        Config config = new Config();
        DistributedHashTableManager manager = new DistributedHashTableManager(config);
        manager.startUp();
        return manager;
    }

    /**
     * creates a new {@link DistributedHashTableManager} with given configs and starts it
     * */
    public static DistributedHashTableManager createNewInstance(Config config) {
        DistributedHashTableManager manager = new DistributedHashTableManager(config);
        manager.startUp();
        return manager;
    }

}
