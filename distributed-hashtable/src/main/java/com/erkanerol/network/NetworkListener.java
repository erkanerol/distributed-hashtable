package com.erkanerol.network;

import com.erkanerol.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * thread that listens a server socket and create new threads for incoming messages
 */
public class NetworkListener extends Thread {

    private static Logger logger = LoggerFactory.getLogger(NetworkListener.class);

    private EventListener eventListener;
    private final ServerSocket serverSocket;
    private volatile boolean isRunning = false;

    private final ExecutorService executor;

    /**
     *
     * @param eventListener the event listener for events read from socket
     * @param port the port of the server socket
     * @param poolSize the pool size of {@link MessageProcessor}
     */
    public NetworkListener(EventListener eventListener, int port, int poolSize) {
        this.eventListener = eventListener;

        try {
            logger.debug("server socket is opening. Port:{}", port);
            this.serverSocket = new ServerSocket(port);
            logger.info("server socket is opened. Port:{}", port);
        } catch (IOException e) {
            logger.error("I/O exception in server socket opening", e);
            throw new NetworkException("I/O exception in server socket opening", e);
        }

        executor = Executors.newFixedThreadPool(poolSize);
    }

    /**
     * listens server socket continuously and start a new {@link MessageProcessor} for each request
     */
    @Override
    public void run() {
        super.run();
        isRunning = true;
        logger.info("network listening is starting");
        Socket socket = null;
        while (isRunning) {
            try {
                socket = serverSocket.accept();
                logger.info("a connection is setup with {}", socket.getInetAddress());
                executor.submit(new MessageProcessor(this.eventListener, socket));
            } catch (IOException e) {
                if (isRunning) {
                    logger.error("I/O error: ", e);
                } else {
                    logger.info("Socket is closed");
                }
            }
        }
    }

    /**
     * closes the server socket
     */
    public void shutdown() {
        try {
            logger.info("Server socket is closing");
            isRunning = false;
            serverSocket.close();
        } catch (IOException e) {
            logger.error("Server socket cannot be closed", e);
        }
    }
}
