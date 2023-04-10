package com.simplespringboot.app.exception.type;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotFoundException extends RuntimeException{

    private String message;

    public NotFoundException(){}

    public NotFoundException(String message){
        super(message);
        this.message = message;
    }
}
