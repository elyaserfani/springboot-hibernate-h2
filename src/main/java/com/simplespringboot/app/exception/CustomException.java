package com.simplespringboot.app.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Setter
@Getter
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
}