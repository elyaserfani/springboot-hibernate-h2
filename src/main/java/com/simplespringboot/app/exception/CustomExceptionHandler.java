package com.simplespringboot.app.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.simplespringboot.app.exception.ErrorResponse;
import com.simplespringboot.app.exception.type.InternalServerErrorException;
import com.simplespringboot.app.utility.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LogManager.getLogger(CustomExceptionHandler.class);

    //Handle All Exceptions
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn(ex.getMessage());
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    //Handle Authentication Exception (Error Code = 401)
    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED,"The user is not authenticated");
        return Utility.buildResponseEntity(errorResponse);
    }

    //Handle Bad Requests Exception (Error Code = 400 , Validation Errors)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid inputs", errors);
        return handleExceptionInternal(ex, errorResponse, headers, errorResponse.getStatus(), request);
    }

    //Handle Not Found Requests Exception (Error Code = 404)
    @Override
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,"Resource not found");
        try {
            String json = objectWriter.writeValueAsString(errorResponse);
            headers.add("Content-Type", "application/json;charset=utf-8");
            return handleExceptionInternal(ex, json, headers, HttpStatus.NOT_FOUND, request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}