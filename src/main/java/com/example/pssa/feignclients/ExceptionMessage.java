package com.example.pssa.feignclients;

import java.util.ArrayList;
import java.util.List;

public class ExceptionMessage {

    private String statusCode;
    private String message;
    private List<String> errors = new ArrayList<>();

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
