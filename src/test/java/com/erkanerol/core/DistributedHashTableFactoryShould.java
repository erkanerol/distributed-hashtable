package com.erkanerol.core;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class DistributedHashTableFactoryShould {




    @Test
    public void createNewInstance(){
        DistributedHashTable<String,String> factory = new DistributedHashTableFactory().createNewInstance(String.class, String.class);
        assertNotNull(factory);
    }
}
