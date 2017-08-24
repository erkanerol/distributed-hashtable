package com.erkanerol.event;
;
import com.erkanerol.event.network.NetworkEvent;

import java.net.Socket;

/**
 * handler that takes network events and makes changes in peer list
 */
public interface NetworkEventHandler {
    void handleNetworkEvent(Socket socket, NetworkEvent networkEvent);
}
