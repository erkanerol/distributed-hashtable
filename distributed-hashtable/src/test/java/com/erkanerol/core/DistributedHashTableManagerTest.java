package com.erkanerol.core;

import com.erkanerol.network.Peer;
import org.junit.Test;

import static org.junit.Assert.*;

public class DistributedHashTableManagerTest {


    @Test
    public void putAndRemoveOneInstance() {
        DistributedHashTableManager manager = DistributedHashTableManagerFactory.createNewInstance();
        DistributedHashTable<Long, String> dht = manager.getDistributedHashTable("usermap");
        dht.put(1l, "Erkan");
        assertEquals("Erkan", dht.get(1l));
        dht.remove(1l);
        assertNull(dht.get(1l));
        manager.shutDown();
    }

    @Test
    public void putGetWithTwoInstance() {
        Config config2 = new Config();
        config2.setPort(Config.DEFAULT_PORT+1);
        config2.addPeer(new Peer(Config.DEFAULT_HOSTNAME, Config.DEFAULT_PORT));

        DistributedHashTableManager manager1 = DistributedHashTableManagerFactory.createNewInstance();
        DistributedHashTableManager manager2 = DistributedHashTableManagerFactory.createNewInstance(config2);


        DistributedHashTable<Long, String> map1 = manager1.getDistributedHashTable("userMap");
        DistributedHashTable<Long, String> map2 = manager2.getDistributedHashTable("userMap");


        map1.put(1l, "Erkan");
        waitForNetWorkLatency();
        assertEquals("Erkan", map2.get(1l));

        manager1.shutDown();
        manager2.shutDown();
    }




    @Test
    public void get_attendToNetworkAfterPut_FindsValue() {
        Config config2 = new Config();
        config2.setPort(Config.DEFAULT_PORT+1);
        config2.addPeer(new Peer(Config.DEFAULT_HOSTNAME, Config.DEFAULT_PORT));
                


        DistributedHashTableManager manager1 = DistributedHashTableManagerFactory.createNewInstance();
        DistributedHashTable<Long, String> map1 = manager1.getDistributedHashTable("userMap");
        map1.put(1l, "Erkan");

        waitForNetWorkLatency();

        DistributedHashTableManager manager2 = DistributedHashTableManagerFactory.createNewInstance(config2);
        DistributedHashTable<Long, String> map2 = manager2.getDistributedHashTable("userMap");


        assertEquals("Erkan", map2.get(1l));

        manager1.shutDown();
        manager2.shutDown();
    }

    @Test
    public void putGetRemoveWithThreeInstance() {
        Config config2 = new Config();
        config2.setPort(Config.DEFAULT_PORT+1);
        config2.addPeer(new Peer(Config.DEFAULT_HOSTNAME, Config.DEFAULT_PORT));
                

        Config config3 = new Config();
        config3.setPort(Config.DEFAULT_PORT+2);
        config3.addPeer(new Peer(Config.DEFAULT_HOSTNAME, Config.DEFAULT_PORT));
        config3.addPeer(new Peer(Config.DEFAULT_HOSTNAME, Config.DEFAULT_PORT+1));
               

        DistributedHashTableManager manager1 = DistributedHashTableManagerFactory.createNewInstance();
        DistributedHashTableManager manager2 = DistributedHashTableManagerFactory.createNewInstance(config2);


        DistributedHashTable<Long, String> map2 = manager2.getDistributedHashTable("userMap");
        map2.put(1l, "Erkan");


        DistributedHashTable<Long, String> map1 = manager1.getDistributedHashTable("userMap");

        waitForNetWorkLatency();


        assertEquals("Erkan", map1.get(1l));

        DistributedHashTableManager manager3 = DistributedHashTableManagerFactory.createNewInstance(config3);

        manager3.shutDown();


        map2.remove(1l);


        waitForNetWorkLatency();

        assertNull(map1.get(1l));

        manager1.shutDown();
        manager2.shutDown();
    }


    private void waitForNetWorkLatency() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
