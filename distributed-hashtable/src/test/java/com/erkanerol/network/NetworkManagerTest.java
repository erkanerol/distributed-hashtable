package com.erkanerol.network;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.Test;

import com.erkanerol.core.Config;
import com.erkanerol.core.DistributedHashTableManager;

public class NetworkManagerTest {

    @Test
    public void open_AtTheBeginning_OpenServerSocket(){

        DistributedHashTableManager manager = mock(DistributedHashTableManager.class);
        Config config = new Config();
        NetworkManager networkManager = new NetworkManager(manager,config);

        networkManager.open();
        assertTrue(checkPortIsOpen(config.getPort()));
        networkManager.close();
    }

    @Test
    public void open_AlreadyOpened_ThrowsNetworkException(){

        DistributedHashTableManager manager = mock(DistributedHashTableManager.class);
        Config config = new Config();
        NetworkManager networkManager = new NetworkManager(manager,config);

        networkManager.open();

        try{
            networkManager.open();
            assert false;
        }catch (IllegalStateException ne){

        }finally {
            networkManager.close();
        }

    }

    @Test
    public void close_AtTheEnd_CloseServerSocket(){

        DistributedHashTableManager manager = mock(DistributedHashTableManager.class);
        Config config = new Config();
        NetworkManager networkManager = new NetworkManager(manager,config);

        networkManager.open();
        waitForThreadLatency();
        networkManager.close();
        assertFalse(checkPortIsOpen(config.getPort()));
    }


    @Test
    public void close_BeforeOpen_ThrowsNetworkException(){

        DistributedHashTableManager manager = mock(DistributedHashTableManager.class);
        Config config = new Config();
        NetworkManager networkManager = new NetworkManager(manager,config);

        try{
            networkManager.close();
            assert false;
        }catch (IllegalStateException ne){

        }
    }

    private boolean checkPortIsOpen(int port){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.close();
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    private void waitForThreadLatency() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
