package com.erkanerol.network;

import com.erkanerol.core.Config;
import com.erkanerol.core.DistributedHashTable;
import com.erkanerol.core.DistributedHashTableManager;
import com.erkanerol.event.Event;
import com.erkanerol.event.EventListener;
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

public class NetworkManager implements EventListener {

    private static Logger logger = LoggerFactory.getLogger(NetworkManager.class);


    private final int port;
    private final String hostname;
    private List<Peer> peerList;
    private int poolSize;

    private NetworkListener netWorkListener;
    private DistributedHashTableManager distributedHashTableManager;


    public NetworkManager(DistributedHashTableManager distributedHashTableManager, Config config) {
        this.distributedHashTableManager = distributedHashTableManager;
        this.hostname = config.getHostname();
        this.port = config.getPort();
        this.peerList = config.getPeerList();
        this.poolSize = config.getPoolSize();
    }

    public void open() {
        logger.info("network lister is starting");
        this.netWorkListener = new NetworkListener(this, this.port, this.poolSize);
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


    public void close() {
        this.netWorkListener.shutdown();

        logger.info("leave event is propagating");
        propagate(new LeaveEvent(this.hostname, this.port));
    }

    public synchronized void propagate(Event event) {

        if (peerList != null) {
            for (Peer peer : peerList) {
                sendEventToPeer(peer, event);
            }
        }

    }


    @Override
    public synchronized void processEvent(Socket socket, Event event) {
        if (event instanceof TableEvent) {
            distributedHashTableManager.handleTableEvent((TableEvent) event);
        } else {
            processNetworkEvent(socket, (NetworkEvent) event);
        }
    }

    private void processNetworkEvent(Socket socket, NetworkEvent networkEvent) {
        if (networkEvent instanceof AttendEvent) {
            AttendEvent attendEvent = (AttendEvent) networkEvent;
            Peer peer = new Peer(attendEvent.getHostname(), attendEvent.getPort());
            this.peerList.add(peer);

            if (attendEvent.isInitialStateRequest()) {
                writeAllStateToSocket(socket);
            }
            logger.info("New peer is added {}", peer);
        } else if (networkEvent instanceof LeaveEvent) {
            LeaveEvent leaveEvent = (LeaveEvent) networkEvent;
            Peer peer = new Peer(leaveEvent.getHostname(), leaveEvent.getPort());
            logger.info("A peer is gonna leave. size: {}", this.peerList.size());
            this.peerList.remove(peer);
            logger.info("A peer is leaved {}  new size:{}", peer, this.peerList.size());
        }
    }

    private void writeAllStateToSocket(Socket socket) {

        try {
            Map<String, DistributedHashTable> maps = distributedHashTableManager.exportTables();
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(maps);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean sendEventToPeer(Peer peer, Event event) {
        Socket socket = null;
        try {
            logger.info("Event: {} is sending to peer: {}", event, peer);
            socket = new Socket(peer.getHostname(), peer.getPort());

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(event);

            if (event instanceof AttendEvent) {
                AttendEvent attendEvent = (AttendEvent) event;
                if (attendEvent.isInitialStateRequest()) {
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Map<String, DistributedHashTable> maps = (Map<String, DistributedHashTable>) ois.readObject();
                    distributedHashTableManager.importTables(maps);
                    socket.close();
                }
            }
            logger.info("Socket is closing.");
            return true;
        } catch (IOException e) {
            logger.info("The message cannot be sent. The node may be leaved.");
            return false;
        } catch (ClassNotFoundException e) {
            logger.error("Unexpected error");
            return false;
        }

    }

}
