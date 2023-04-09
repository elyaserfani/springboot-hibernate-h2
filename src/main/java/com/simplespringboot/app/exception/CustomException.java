package com.simplespringboot.app.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class CustomException {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public CustomException(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public CustomException(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }

    public CustomException(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
        this.errors = null;
    }
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}