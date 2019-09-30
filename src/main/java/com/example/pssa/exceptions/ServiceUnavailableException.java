package com.example.pssa.exceptions;

public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException(String message) {
        super(message);
    }

    public ServiceUnavailableException(String message, Throwable ex) {
        super(message, ex);
    }
}
