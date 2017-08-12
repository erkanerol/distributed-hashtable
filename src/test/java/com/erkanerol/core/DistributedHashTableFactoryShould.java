package com.erkanerol.core;

import com.erkanerol.network.Peer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DistributedHashTableFactoryShould {




    @Test
    public void createNewInstance(){
        Config config = new Config(9876);
        DistributedHashTableManager manager = new DistributedHashTableManagerFactory().createNewInstance(config);
        assertNotNull(manager);
    }


    @Test
    public void createNewMap(){
        Config config = new Config(9876);
        DistributedHashTableManager manager = new DistributedHashTableManagerFactory().createNewInstance(config);
        DistributedHashTable<Long,String> dht = manager.getDistributedHashTable("usermap");
        dht.put(1l,"Erkan");
        assertEquals("Erkan",dht.get(1l));
        dht.remove(1l);
        assertNull(dht.get(1l));

    }


    @Test
    public void attend(){
        Config config1 = new Config(9876);


        Peer peer = new Peer("localhost",9876);
        List<Peer> peerList = new ArrayList<>();
        peerList.add(peer);
        Config config2 = new Config(9877,peerList);

        DistributedHashTableManager manager1 = new DistributedHashTableManagerFactory().createNewInstance(config1);
        DistributedHashTableManager manager2 = new DistributedHashTableManagerFactory().createNewInstance(config2);

        DistributedHashTable<Long, String> map2 = manager2.getDistributedHashTable("userMap");
        map2.put(1l,"Erkan");



        DistributedHashTable<Long, String> map1 = manager1.getDistributedHashTable("userMap");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        assertEquals("Erkan",map1.get(1l));

        map2.remove(1l);


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertNull(map1.get(1l));


    }
}
