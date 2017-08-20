package com.erkanerol.examples;

import com.erkanerol.core.Config;
import com.erkanerol.core.DistributedHashTable;
import com.erkanerol.core.DistributedHashTableManager;
import com.erkanerol.core.DistributedHashTableManagerFactory;
import com.erkanerol.network.Peer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CliAppWithTable {

    public static void main(String[] args) throws Exception {
        Config config = getConfigsFromArguments(args);
        DistributedHashTableManager manager = DistributedHashTableManagerFactory.createNewInstance(config);
        DistributedHashTable<String,String> restCache = manager.getDistributedHashTable("restCache");


        commandLoop: while (true){
            System.out.println("PLEASE ENTER NEXT COMMAND");
            System.out.println("***** GET key");
            System.out.println("***** PUT key value");
            System.out.println("***** EXIT");


            Scanner scanner = new Scanner(System.in);
            String command = scanner.next().toUpperCase();

            switch (command){
                case "GET":
                    String key = scanner.next();
                    System.out.println(restCache.get(key));
                    break;
                case "PUT":
                    String key2 = scanner.next();
                    String value = scanner.next();
                    restCache.put(key2,value);
                    System.out.println("The value is put");
                    break;
                case "EXIT":
                    manager.shutDown();
                    break commandLoop;
                default:
                    System.out.println("UNDEFINED COMMAND");
            }
            System.out.println("\n\n");
        }

    }

    private static Config getConfigsFromArguments(String[] args) {
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        // add peers to config
        if(args.length > 2){
            List<Peer> peerList = new ArrayList<>();

            for (int i=2; i<args.length; i=i+2){
                Peer peer = new Peer(args[i],Integer.parseInt(args[i+1]));
                peerList.add(peer);
            }

            return new Config(hostname,port,peerList);
        }else {
            return new Config(hostname,port);
        }
    }
}
