package com.erkanerol.core;

import com.erkanerol.network.Peer;
import org.junit.Test;

import static org.junit.Assert.*;

public class DistributedHashTableFactoryShould {


    @Test
    public void createNewInstance() {
        Config config = ConfigBuilder.builder().createConfig();
        DistributedHashTableManager manager = DistributedHashTableManagerFactory.createNewInstance(config);
        assertNotNull(manager);
        manager.shutDown();
    }


    @Test
    public void createNewMap() {
        Config config = ConfigBuilder.builder().createConfig();
        DistributedHashTableManager manager = DistributedHashTableManagerFactory.createNewInstance(config);
        DistributedHashTable<Long, String> dht = manager.getDistributedHashTable("usermap");
        dht.put(1l, "Erkan");
        assertEquals("Erkan", dht.get(1l));
        dht.remove(1l);
        assertNull(dht.get(1l));
        manager.shutDown();
    }

    @Test
    public void putGet() {
        Config config1 = ConfigBuilder.builder().createConfig();
        Config config2 = ConfigBuilder.builder().setPort(9879)
                .addPeer(new Peer("localhost", 9878))
                .createConfig();

        DistributedHashTableManager manager1 = DistributedHashTableManagerFactory.createNewInstance(config1);
        DistributedHashTableManager manager2 = DistributedHashTableManagerFactory.createNewInstance(config2);


        DistributedHashTable<Long, String> map1 = manager1.getDistributedHashTable("userMap");
        map1.put(1l, "Erkan");


        DistributedHashTable<Long, String> map2 = manager2.getDistributedHashTable("userMap");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("Erkan", map2.get(1l));

        manager1.shutDown();
        manager2.shutDown();
    }


    @Test
    public void attend() {
        Config config1 = ConfigBuilder.builder().createConfig();
        Config config2 = ConfigBuilder.builder().setPort(9879)
                .addPeer(new Peer("localhost", 9878))
                .createConfig();


        DistributedHashTableManager manager1 = DistributedHashTableManagerFactory.createNewInstance(config1);
        DistributedHashTable<Long, String> map1 = manager1.getDistributedHashTable("userMap");
        map1.put(1l, "Erkan");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DistributedHashTableManager manager2 = DistributedHashTableManagerFactory.createNewInstance(config2);
        DistributedHashTable<Long, String> map2 = manager2.getDistributedHashTable("userMap");


        assertEquals("Erkan", map2.get(1l));

        manager1.shutDown();
        manager2.shutDown();
    }

    @Test
    public void putGetRemove() {
        Config config1 = ConfigBuilder.builder().createConfig();
        Config config2 = ConfigBuilder.builder().setPort(9879)
                .addPeer(new Peer("localhost", 9878))
                .createConfig();

        Config config3 = ConfigBuilder.builder().setPort(9880)
                .addPeer(new Peer("localhost", 9878))
                .addPeer(new Peer("localhost", 9879))
                .createConfig();

        DistributedHashTableManager manager1 = DistributedHashTableManagerFactory.createNewInstance(config1);
        DistributedHashTableManager manager2 = DistributedHashTableManagerFactory.createNewInstance(config2);


        DistributedHashTable<Long, String> map2 = manager2.getDistributedHashTable("userMap");
        map2.put(1l, "Erkan");


        DistributedHashTable<Long, String> map1 = manager1.getDistributedHashTable("userMap");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        assertEquals("Erkan", map1.get(1l));

        DistributedHashTableManager manager3 = DistributedHashTableManagerFactory.createNewInstance(config3);

        manager3.shutDown();


        map2.remove(1l);


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertNull(map1.get(1l));

        manager1.shutDown();
        manager2.shutDown();
    }
}
