package com.erkanerol.network;

import com.erkanerol.events.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkListener extends Thread {

    Logger logger = LoggerFactory.getLogger(NetworkListener.class);

    private EventListener eventListener;
    private final ServerSocket serverSocket;
    private volatile boolean isRunning = false;

    public NetworkListener(EventListener eventListener, int port) {
        this.eventListener = eventListener;

        try {
            logger.info("server socket is opening");
            this.serverSocket = new ServerSocket(port);
            logger.info("server socket is opened");
        } catch (IOException e) {
            logger.error("I/O exception in server socket opening",e);
            throw new NetworkException("I/O exception in server socket opening",e);
        }
    }

    @Override
    public void run() {
        super.run();
        isRunning = true;
        logger.info("network listening is starting");
        Socket socket = null;
        while(isRunning){
            try {
                socket = serverSocket.accept();
                logger.info("a connection is setup with {}",socket.getInetAddress());
                new MessageProcessor(this.eventListener,socket).start();
            } catch (IOException e) {
                if (isRunning){
                    logger.error("I/O error: ", e);
                }else {
                    logger.info("Socket is closed");
                }
            }
        }
    }

    public void shutdown() {
        try {
            logger.info("Server socket is closing");
            isRunning = false;
            this.serverSocket.close();
        } catch (IOException e) {
            logger.error("Server socket cannot be closed",e);
        }
    }
}
