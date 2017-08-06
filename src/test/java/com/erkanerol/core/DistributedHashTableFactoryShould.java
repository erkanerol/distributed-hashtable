package com.erkanerol.core;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class DistributedHashTableFactoryShould {




    @Test
    public void createNewInstance(){
        DistributedHashTableManager manager = new DistributedHashTableManagerFactory().createNewInstance(new Config("127.0.0.1",9876));
        assertNotNull(manager);
    }


    @Test
    public void createNewMap(){
        DistributedHashTableManager manager = new DistributedHashTableManagerFactory().createNewInstance(new Config("127.0.0.1",9876));
        DistributedHashTable<Long,String> dht = manager.createDistributedHashTable("usermap",Long.class,String.class);
        dht.put(1l,"Erkan");
        assertEquals("Erkan",dht.get(1l));

    }
}
