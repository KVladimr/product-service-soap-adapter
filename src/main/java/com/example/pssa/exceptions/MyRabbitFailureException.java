package com.example.pssa.exceptions;

public class MyRabbitFailureException extends RuntimeException {

    public MyRabbitFailureException(String message) {
        super(message);
    }
}
