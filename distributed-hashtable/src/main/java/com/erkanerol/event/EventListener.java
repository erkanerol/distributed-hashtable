package com.erkanerol.event;

import java.net.Socket;

public interface EventListener {
    void processEvent(Socket socket, Event event);
}
