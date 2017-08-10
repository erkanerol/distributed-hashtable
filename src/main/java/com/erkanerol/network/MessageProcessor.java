package com.erkanerol.network;

import com.erkanerol.events.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class MessageProcessor extends Thread{

    Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    private final Socket socket;

    public MessageProcessor(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        super.run();

        try {
            InputStream stream = this.socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(stream);
            Event event = (Event) ois.readObject();
            logger.info("Event is get:"+event.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
