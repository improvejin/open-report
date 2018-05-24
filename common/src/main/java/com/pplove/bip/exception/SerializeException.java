package com.pplove.bip.exception;

public class SerializeException extends RuntimeException {

    public SerializeException(String message, Exception cause){
        super(message,cause);
    }
}
