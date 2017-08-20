package com.erkanerol.events;

import java.io.Serializable;

public abstract class Event implements Serializable{
    public abstract EventType getType();

}
