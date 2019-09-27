package com.example.pssa.exceptions;

import org.springframework.ws.WebServiceException;

public class WebServiceLoggingException extends WebServiceException {

    public WebServiceLoggingException(String message) {
        super(message);
    }

    public WebServiceLoggingException(String message, Throwable cause) {
        super(message, cause);
    }
}
