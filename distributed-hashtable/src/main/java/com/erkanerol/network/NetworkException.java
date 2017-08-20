package com.erkanerol.network;

/**
 * exception used to handle network problems
 */
public class NetworkException extends RuntimeException {

    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetworkException(String message) {
        super(message);
    }
}
