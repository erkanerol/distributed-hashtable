package com.erkanerol.network;

import com.erkanerol.event.Event;
import com.erkanerol.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * thread used to read an event coming from a client and call the event listener
 */
public class MessageProcessor extends Thread {

    private static Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    private EventListener eventListener;
    private final Socket socket;

    /**
     *
     * @param eventListener the listener that is going to call for event read from the socket
     * @param socket the socket that is going to read
     */
    public MessageProcessor(EventListener eventListener, Socket socket) {
        this.eventListener = eventListener;
        this.socket = socket;
    }


    /**
     * reads the socket, converts the input into an event and calls the listener
     *
     */
    @Override
    public void run() {
        super.run();

        try {
            logger.debug("Message processing  is started: {} {}", socket.getInetAddress().getHostAddress(), socket.getPort());
            ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream());
            Event event = (Event) ois.readObject();
            logger.debug("Event is get:" + event.toString());
            eventListener.processEvent(this.socket, event);
            socket.close();
            logger.debug("socket is closing");
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Message comes from a peer cannot be read. Exception in reading socket", e);
        }
    }

}
