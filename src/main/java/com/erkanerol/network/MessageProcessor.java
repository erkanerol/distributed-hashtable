package com.erkanerol.network;

import com.erkanerol.events.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MessageProcessor extends Thread{

    Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    private NetworkManager networkManager;
    private final Socket socket;

    public MessageProcessor(NetworkManager networkManager, Socket socket) {
        this.networkManager = networkManager;
        this.socket = socket;
    }


    @Override
    public void run() {
        super.run();

        try {

            logger.info("Message processing  is started: {} {}", socket.getInetAddress().getHostAddress(),socket.getPort());
            ObjectOutputStream oos = new ObjectOutputStream(this.socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream());
            logger.info("Message is reading");
            Event event = (Event) ois.readObject();
            logger.info("Event is get:"+event.toString());
            networkManager.processEvent (socket,event);
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Exception in reading socket", e);
        }
    }

}
