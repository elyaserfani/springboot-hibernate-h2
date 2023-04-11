package com.simplespringboot.app.exception.handler;

import com.simplespringboot.app.exception.CustomExceptionHandler;
import com.simplespringboot.app.exception.ErrorResponse;
import com.simplespringboot.app.exception.type.InternalServerErrorException;
import com.simplespringboot.app.utility.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class InternalServerErrorHandler {

    private static final Logger logger = LogManager.getLogger(CustomExceptionHandler.class);
    //Handle Internal Server Error (Error Code = 500)
    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleInternalServerErrorException(InternalServerErrorException ex) {
        logger.error(ex.getMessage());
        return Utility.buildResponseEntity(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage()));
    }
}
