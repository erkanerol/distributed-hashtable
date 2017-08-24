package com.erkanerol.network;

import com.erkanerol.core.Config;
import com.erkanerol.core.DistributedHashTable;
import com.erkanerol.core.DistributedHashTableManager;
import com.erkanerol.event.Event;
import com.erkanerol.event.EventListener;
import com.erkanerol.event.EventListenerImpl;
import com.erkanerol.event.NetworkEventHandler;
import com.erkanerol.event.map.TableEvent;
import com.erkanerol.event.network.AttendEvent;
import com.erkanerol.event.network.LeaveEvent;
import com.erkanerol.event.network.NetworkEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * a manager class that coordinates all components about network
 */
public class NetworkManager implements NetworkEventHandler {

    private static Logger logger = LoggerFactory.getLogger(NetworkManager.class);


    private final int port;
    private final String hostname;
    private List<Peer> peerList;
    private int poolSize;

    private NetworkListener netWorkListener;
    private DistributedHashTableManager distributedHashTableManager;


    /**
     *
     * @param distributedHashTableManager
     * @param config
     */
    public NetworkManager(DistributedHashTableManager distributedHashTableManager, Config config) {
        this.distributedHashTableManager = distributedHashTableManager;
        this.hostname = config.getHostname();
        this.port = config.getPort();
        this.peerList = config.getPeerList();
        this.poolSize = config.getPoolSize();
    }

    /**
     * opens the network listener, sends attend event to other nodes and gets initial state from other nodes
     *
     */
    public void open() {

        if (this.netWorkListener != null && this.netWorkListener.isRunning()) {
            throw new IllegalStateException("Network Manager is already opened");
        }

        logger.info("network listener is starting");
        EventListener eventListener = new EventListenerImpl(this.distributedHashTableManager, this);
        this.netWorkListener = new NetworkListener(eventListener, this.port, this.poolSize);
        this.netWorkListener.start();

        logger.info("attend event is propagating");

        boolean isFirstPeer = true;
        if (peerList != null) {
            for (Peer peer : peerList) {
                boolean isSent = sendEventToPeer(peer, new AttendEvent(this.hostname, this.port, isFirstPeer));
                if (isSent) {
                    isFirstPeer = false;
                }
            }
        }
    }

    /**
     * closes the network listener, sends leave event to other nodes
     */
    public void close() {

        if (this.netWorkListener == null || !this.netWorkListener.isRunning()) {
            throw new IllegalStateException("Network Manager is not running");
        }

        this.netWorkListener.shutdown();

        logger.info("leave event is propagating");
        propagate(new LeaveEvent(this.hostname, this.port));
    }

    /**
     * sends an event to other nodes
     * @param event the event that is going to be sent
     */
    public synchronized void propagate(Event event) {

        if (peerList != null) {
            for (Peer peer : peerList) {
                sendEventToPeer(peer, event);
            }
        }

    }

    /**
     * exports all tables from manager and writes them into socket
     * @param socket the socket that the response will be written
     */
    public void writeAllStateToSocket(Socket socket) {

        try {
            Map<String, DistributedHashTable> maps = distributedHashTableManager.exportTables();
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(maps);
        } catch (IOException e) {
            logger.error("Error in sending initial state to new peer",e);
        }

    }

    /**
     * sends an event to a peer and reads the response if necessary
     * @param peer the peer that the event will be sent
     * @param event the event that will be sent
     */
    private boolean sendEventToPeer(Peer peer, Event event) {
        Socket socket = null;
        try {
            logger.debug("Event: {} is sending to peer: {}", event, peer);
            socket = new Socket(peer.getHostname(), peer.getPort());

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(event);

            if (event instanceof AttendEvent) {
                AttendEvent attendEvent = (AttendEvent) event;
                if (attendEvent.isInitialStateRequest()) {
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Map<String, DistributedHashTable> maps = (Map<String, DistributedHashTable>) ois.readObject();
                    distributedHashTableManager.importTables(maps);
                }
            }
            socket.close();
            logger.debug("Socket is closing.");
            return true;
        } catch (IOException e) {
            logger.info("The message cannot be sent. The node may be leaved from the network or temporarily inaccessible.");
            return false;
        } catch (ClassNotFoundException e) {
            logger.error("Unexpected error");
            return false;
        }

    }



    /**
     * handles network event an write response to socket if necessary
     *
     * the method is synchronized and the manager waits for
     * writing initial state to new peer if it requests. since it uses the peer list
     * in {@link #propagate(Event)} and has to be sure the new peer state is valid.
     *
     * since we cannot
     * send new events before finishing the process to
     * @param socket the socket that the response will be written
     * @param networkEvent the event that will be handled
     */
    @Override
    public synchronized void handleNetworkEvent(Socket socket, NetworkEvent networkEvent) {
        Peer peer = new Peer(networkEvent.getHostname(), networkEvent.getPort());

        if (networkEvent instanceof AttendEvent) {
            logger.debug("A peer is gonna attend. size: {}", this.peerList.size());
            AttendEvent attendEvent = (AttendEvent) networkEvent;

            this.peerList.add(peer);


            if (attendEvent.isInitialStateRequest()) {
                writeAllStateToSocket(socket);
            }

            logger.info("A peer is attended {}  , peer list size:{}", peer, this.peerList.size());
        } else if (networkEvent instanceof LeaveEvent) {
            logger.debug("A peer is gonna be removed from list. size: {}", this.peerList.size());
            this.peerList.remove(peer);
            logger.info("A peer is leaved {}  , peer list size:{}", peer, this.peerList.size());
        }
    }

}
