package com.erkanerol.event;

import java.net.Socket;

/**
 * listener that process the events came from other nodes via network
 */
public interface EventListener {
    void processEvent(Socket socket, Event event);
}
