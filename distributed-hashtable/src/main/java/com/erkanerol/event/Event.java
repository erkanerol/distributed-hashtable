package com.erkanerol.event;

import java.io.Serializable;

/**
 * abstract class that is used to transfer changes in one node to other nodes in the network
 */
public abstract class Event implements Serializable {
    public abstract EventType getType();

}
