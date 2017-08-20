package com.erkanerol.network;

import com.erkanerol.core.Config;
import com.erkanerol.core.ConfigBuilder;
import com.erkanerol.core.DistributedHashTableManager;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class NetworkManagerTest {

    @Test
    public void open_AtTheBeginning_OpenServerSocket(){

        DistributedHashTableManager manager = mock(DistributedHashTableManager.class);
        Config config = ConfigBuilder.builder().createConfig();
        NetworkManager networkManager = new NetworkManager(manager,config);

        networkManager.open();
        assertTrue(checkPortIsOpen(config.getPort()));
        networkManager.close();
    }

    @Test
    public void open_AlreadyOpened_ThrowsNetworkException(){

        DistributedHashTableManager manager = mock(DistributedHashTableManager.class);
        Config config = ConfigBuilder.builder().createConfig();
        NetworkManager networkManager = new NetworkManager(manager,config);

        networkManager.open();

        try{
            networkManager.open();
            assert false;
        }catch (NetworkException ne){

        }finally {
            networkManager.close();
        }

    }

    @Test
    public void close_AtTheEnd_CloseServerSocket(){

        DistributedHashTableManager manager = mock(DistributedHashTableManager.class);
        Config config = ConfigBuilder.builder().createConfig();
        NetworkManager networkManager = new NetworkManager(manager,config);

        networkManager.open();
        waitForThreadLatency();
        networkManager.close();
        assertFalse(checkPortIsOpen(config.getPort()));
    }


    @Test
    public void close_BeforeOpen_ThrowsNetworkException(){

        DistributedHashTableManager manager = mock(DistributedHashTableManager.class);
        Config config = ConfigBuilder.builder().createConfig();
        NetworkManager networkManager = new NetworkManager(manager,config);

        try{
            networkManager.close();
            assert false;
        }catch (NetworkException ne){

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
