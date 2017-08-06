package com.erkanerol.network;

import com.erkanerol.events.MapEvent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkManager {

    private final int port;
    private final String host;

    public NetworkManager(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void open(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Socket socket = null;
        while(true){
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }

            new MessageProcessorThread(socket).start();
        }
    }


    public void propagate(MapEvent event) {

    }
}
