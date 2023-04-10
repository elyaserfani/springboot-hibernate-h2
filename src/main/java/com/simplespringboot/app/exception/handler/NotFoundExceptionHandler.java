package com.simplespringboot.app.exception.handler;


import com.simplespringboot.app.exception.ErrorResponse;
import com.simplespringboot.app.exception.type.NotFoundException;
import com.simplespringboot.app.utility.Utility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundExceptionHandler {

    //Handle Not Founds Exception (Error Code = 404)
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ResponseEntity<?> handleUserNotFoundException(NotFoundException notFoundException){
        return Utility.buildResponseEntity(new ErrorResponse(HttpStatus.NOT_FOUND,notFoundException.getMessage()));
    }
}
