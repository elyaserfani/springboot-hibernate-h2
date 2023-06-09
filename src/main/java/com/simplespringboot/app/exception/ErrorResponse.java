package com.simplespringboot.app.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;


@Getter
@Setter
public class ErrorResponse {

    private HttpStatus status;
    private String message;
    private List<String> details;

    public ErrorResponse() {}

    public ErrorResponse(String message){
        super();
        this.message = message;
    }
    public ErrorResponse(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(HttpStatus status, String message , List<String> details){
        this.status = status;
        this.message = message;
        this.details = details;
    }

}