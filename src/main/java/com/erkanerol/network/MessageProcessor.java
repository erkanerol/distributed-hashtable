package com.erkanerol.network;

import com.erkanerol.events.Event;
import com.erkanerol.events.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class MessageProcessor extends Thread{

    Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    private EventListener eventListener;
    private final Socket socket;

    public MessageProcessor(EventListener eventListener, Socket socket) {
        this.eventListener = eventListener;
        this.socket = socket;
    }


    @Override
    public void run() {
        super.run();

        try {

            logger.info("Message processing  is started: {} {}", socket.getInetAddress().getHostAddress(),socket.getPort());
            ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream());
            logger.info("Message is reading");
            Event event = (Event) ois.readObject();
            logger.info("Event is get:"+event.toString());
            eventListener.processEvent (this.socket, event);
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Exception in reading socket", e);
        }
    }

}
