package com.erkanerol.event;

import com.erkanerol.event.map.TableEvent;
import com.erkanerol.event.network.NetworkEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;

/**
 * listens events and passes them to handlers according to their types
 */
public class EventListenerImpl implements EventListener {

    private static Logger logger = LoggerFactory.getLogger(EventListenerImpl.class);


    private TableEventHandler tableEventHandler;
    private NetworkEventHandler networkEventHandler;

    public EventListenerImpl(TableEventHandler tableEventHandler, NetworkEventHandler networkEventHandler) {
        this.tableEventHandler = tableEventHandler;
        this.networkEventHandler = networkEventHandler;
    }


    /**
     * detects event type and calls the responding handler
     * @param socket the socket that the response will be written
     * @param event the event that will be handled
     */
    @Override
    public void processEvent(Socket socket, Event event) {
        if (event instanceof TableEvent) {
            tableEventHandler.handleTableEvent((TableEvent) event);
        } else {
            networkEventHandler.handleNetworkEvent(socket, (NetworkEvent) event);
        }
    }

}
