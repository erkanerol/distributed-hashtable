package com.erkanerol.core;

import com.erkanerol.network.Peer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DistributedHashTableFactoryShould {




    @Test
    public void createNewInstance(){
        Config config = new Config("127.0.0.1",9876);
        DistributedHashTableManager manager = DistributedHashTableManagerFactory.createNewInstance(config);
        assertNotNull(manager);
        manager.shutDown();
    }


    @Test
    public void createNewMap(){
        Config config = new Config("127.0.0.1",9876);
        DistributedHashTableManager manager = DistributedHashTableManagerFactory.createNewInstance(config);
        DistributedHashTable<Long,String> dht = manager.getDistributedHashTable("usermap");
        dht.put(1l,"Erkan");
        assertEquals("Erkan",dht.get(1l));
        dht.remove(1l);
        assertNull(dht.get(1l));
        manager.shutDown();
    }

    @Test
    public void putGet(){
        Config config1 = new Config("127.0.0.1",9876);


        Peer peer1 = new Peer("127.0.0.1",9876);
        List<Peer> peerList1 = new ArrayList<>();
        peerList1.add(peer1);

        Config config2 = new Config("127.0.0.1",9877,peerList1);

        DistributedHashTableManager manager1 = DistributedHashTableManagerFactory.createNewInstance(config1);
        DistributedHashTableManager manager2 = DistributedHashTableManagerFactory.createNewInstance(config2);


        DistributedHashTable<Long, String> map1 = manager1.getDistributedHashTable("userMap");
        map1.put(1l,"Erkan");


        DistributedHashTable<Long, String> map2 = manager2.getDistributedHashTable("userMap");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("Erkan",map2.get(1l));

        manager1.shutDown();
        manager2.shutDown();
    }


    @Test
    public void attend(){
        Config config1 = new Config("127.0.0.1",9876);


        Peer peer1 = new Peer("127.0.0.1",9876);
        List<Peer> peerList1 = new ArrayList<>();
        peerList1.add(peer1);

        Config config2 = new Config("127.0.0.1",9877,peerList1);

        DistributedHashTableManager manager1 = DistributedHashTableManagerFactory.createNewInstance(config1);
        DistributedHashTable<Long, String> map1 = manager1.getDistributedHashTable("userMap");
        map1.put(1l,"Erkan");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DistributedHashTableManager manager2 = DistributedHashTableManagerFactory.createNewInstance(config2);
        DistributedHashTable<Long, String> map2 = manager2.getDistributedHashTable("userMap");


        assertEquals("Erkan",map2.get(1l));

        manager1.shutDown();
        manager2.shutDown();
    }

    @Test
    public void putGetRemove(){
        Config config1 = new Config("127.0.0.1",9876);


        Peer peer1 = new Peer("127.0.0.1",9876);
        Peer peer2 = new Peer("127.0.0.1",9877);
        List<Peer> peerList1 = new ArrayList<>();
        peerList1.add(peer1);

        List<Peer> peerList2 = new ArrayList<>();
        peerList2.add(peer1);
        peerList2.add(peer2);

        Config config2 = new Config("127.0.0.1",9877,peerList1);
        Config config3 = new Config("127.0.0.1",9878,peerList2);

        DistributedHashTableManager manager1 = DistributedHashTableManagerFactory.createNewInstance(config1);
        DistributedHashTableManager manager2 = DistributedHashTableManagerFactory.createNewInstance(config2);


        DistributedHashTable<Long, String> map2 = manager2.getDistributedHashTable("userMap");
        map2.put(1l,"Erkan");




        DistributedHashTable<Long, String> map1 = manager1.getDistributedHashTable("userMap");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        assertEquals("Erkan",map1.get(1l));

        DistributedHashTableManager manager3 = DistributedHashTableManagerFactory.createNewInstance(config3);

        manager3.shutDown();


        map2.remove(1l);


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertNull(map1.get(1l));

        manager1.shutDown();
        manager2.shutDown();
    }
}
