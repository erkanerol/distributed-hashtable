package com.erkanerol.examples;

import com.erkanerol.core.*;
import com.erkanerol.network.Peer;

import java.util.Scanner;


public class CliAppWithTable {

    public static void main(String[] args) throws Exception {
        Config config = getConfigsFromArguments(args);
        DistributedHashTableManager manager = DistributedHashTableManagerFactory.createNewInstance(config);
        DistributedHashTable<String, String> restCache = manager.getDistributedHashTable("restCache");


        commandLoop:
        while (true) {
            System.out.println("PLEASE ENTER NEXT COMMAND");
            System.out.println("***** GET key");
            System.out.println("***** PUT key value");
            System.out.println("***** REMOVE key");
            System.out.println("***** EXIT");


            Scanner scanner = new Scanner(System.in);
            String command = scanner.next().toUpperCase();

            switch (command) {
                case "GET":
                    String key = scanner.next();
                    System.out.println(restCache.get(key));
                    break;
                case "PUT":
                    String key2 = scanner.next();
                    String value = scanner.next();
                    restCache.put(key2, value);
                    System.out.println("The value is put");
                    break;
                case "REMOVE":
                    String key3 = scanner.next();
                    restCache.remove(key3);
                    System.out.println("The value is removed");
                    break;
                case "EXIT":
                    manager.shutDown();
                    System.out.println("Manager is closed");
                    break commandLoop;
                default:
                    System.out.println("UNDEFINED COMMAND");
            }
            System.out.println("\n\n");
        }

    }

    private static Config getConfigsFromArguments(String[] args) {

        ConfigBuilder builder = ConfigBuilder.builder();

        if (args.length > 1) {
            builder.setHostName(args[0]);
            builder.setPort(Integer.parseInt(args[1]));
        }

        // add peers to config
        if (args.length > 3) {
            for (int i = 2; i < args.length; i = i + 2) {
                builder.addPeer(new Peer(args[i], Integer.parseInt(args[i + 1])));
            }
        }

        return builder.createConfig();
    }
}
