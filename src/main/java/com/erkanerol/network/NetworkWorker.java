package com.erkanerol.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkWorker {

    private final int port;
    private final String host;

    public NetworkWorker(int port, String host) {
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

            new MyThread(socket).start();
        }
    }



}
