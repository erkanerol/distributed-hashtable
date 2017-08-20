package com.erkanerol.examples;

import com.erkanerol.core.*;
import com.erkanerol.network.Peer;

import java.util.Scanner;

import static spark.Spark.get;
import static spark.Spark.port;


public class CounterExampleWithTable {

    public static void main(String[] args) throws Exception {
        port(Integer.parseInt(args[0]));
        Config config = getConfigsFromArguments(args);
        DistributedHashTableManager manager = DistributedHashTableManagerFactory.createNewInstance(config);
        DistributedHashTable<String, Integer> counterCache = manager.getDistributedHashTable("counterCache");


        get("/counter", (req, res) -> {
            String query = req.queryParams("query");

            if (query == null){
                return "please call this url with ?query=$QUERY";
            }
            Integer counter = counterCache.get(query);

            if(counter == null){
                counter = 0;
            }

            counterCache.put(query,counter+1);

            return counter+1;
        });

    }

    private static Config getConfigsFromArguments(String[] args) {

        ConfigBuilder builder = ConfigBuilder.builder();

        if (args.length > 2) {
            builder.setHostName(args[1]);
            builder.setPort(Integer.parseInt(args[2]));
        }

        // add peers to config
        if (args.length > 4) {
            for (int i = 3; i < args.length; i = i + 2) {
                builder.addPeer(new Peer(args[i], Integer.parseInt(args[i + 1])));
            }
        }

        return builder.createConfig();
    }
}
