package com.erkanerol.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class DistributedHashTableManagerFactoryTest {


    @Test
    public void createNewInstance_DefaultConfig_ReturnsManager() {
        DistributedHashTableManager manager = DistributedHashTableManagerFactory.createNewInstance();
        assertNotNull(manager);
        manager.shutDown();
    }

    @Test
    public void createNewInstance_CustomConfig_ReturnsManager() {
        Config config = new Config(); 
        config.setPort(9789);
        config.setHostname("erkanerol.github.io");
        DistributedHashTableManager manager = DistributedHashTableManagerFactory.createNewInstance(config);
        assertNotNull(manager);
        manager.shutDown();
    }
}
