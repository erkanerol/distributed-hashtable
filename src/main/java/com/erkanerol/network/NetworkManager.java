package com.erkanerol.network;

import com.erkanerol.core.Config;
import com.erkanerol.core.DistributedHashTableManager;
import com.erkanerol.events.AttendEvent;
import com.erkanerol.events.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class NetworkManager {

    Logger logger = LoggerFactory.getLogger(NetworkListener.class);


    private DistributedHashTableManager distributedHashTableManager;
    private final int port;
    private List<Peer> peerList;

    public NetworkManager(DistributedHashTableManager distributedHashTableManager, Config config) {
        this.distributedHashTableManager = distributedHashTableManager;
        this.port = config.getPort();
        this.peerList = config.getPeerList();
    }

    public void open(){
        ServerSocket serverSocket = null;
        try {
            logger.info("server socket is opening");
            serverSocket = new ServerSocket(this.port);
            logger.info("server socket is opened");
        } catch (IOException e) {
            e.printStackTrace();
        }


        logger.info("network lister is starting");
        new NetworkListener(this,serverSocket).start();

        logger.info("attend event is propagating");
        propagate(new AttendEvent(this.port));
    }


    public void propagate(Event event) {

        if (peerList != null) {
            for (Peer peer : peerList){
                sendEventToPeer(peer,event);
            }
        }

    }

    private void sendEventToPeer(Peer peer, Event event) {
        Socket socket = null;
        try {
            logger.info("Event: {} is sending to peer: {}",event, peer);
            socket = new Socket(peer.getHostname(), peer.getPort());

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(event);
            logger.info("Socket is closing.");
        } catch (IOException e) {
            logger.error("Exception in sending event to peer");
        }

    }


    public void processEvent(Socket socket, Event event) {
        if (event instanceof AttendEvent){
            AttendEvent attendEvent = (AttendEvent) event;
            Peer peer = new Peer(socket.getInetAddress().getHostAddress(),attendEvent.getPort());
            this.peerList.add(peer);
            logger.info("New peer is added {}",peer);
        }
    }
}
