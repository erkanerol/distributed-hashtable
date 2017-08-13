package com.erkanerol.events;

import java.net.Socket;

public interface EventListener {
    void processEvent(Socket socket, Event event);
}
