package com.erkanerol.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkListener extends Thread {

    Logger logger = LoggerFactory.getLogger(NetworkListener.class);

    private NetworkManager networkManager;
    private final ServerSocket serverSocket;

    public NetworkListener(NetworkManager networkManager, ServerSocket serverSocket) {
        this.networkManager = networkManager;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        super.run();

        logger.info("network listening is starting");
        Socket socket = null;
        while(true){
            try {
                socket = serverSocket.accept();
                logger.info("a connection is setup with {}",socket.getInetAddress());
            } catch (IOException e) {
                logger.error("I/O error: ", e);
            }

            new MessageProcessor(this.networkManager,socket).start();
        }
    }

}
