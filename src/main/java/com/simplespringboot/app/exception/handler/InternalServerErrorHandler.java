package com.simplespringboot.app.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplespringboot.app.exception.CustomExceptionHandler;
import com.simplespringboot.app.exception.ErrorResponse;
import com.simplespringboot.app.exception.type.InternalServerErrorException;
import com.simplespringboot.app.utility.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class InternalServerErrorHandler {

    @Autowired
    private RestHighLevelClient client;
    private static final Logger logger = LogManager.getLogger(CustomExceptionHandler.class);
    //Handle Internal Server Error (Error Code = 500)
    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleInternalServerErrorException(InternalServerErrorException ex) throws IOException {
        logger.error(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
        IndexRequest request = new IndexRequest("error");
        ObjectMapper Obj = new ObjectMapper();
        final String json = Obj.writeValueAsString(errorResponse);
        request.source(json, XContentType.JSON);
        client.index(request, RequestOptions.DEFAULT);
        return Utility.buildResponseEntity(errorResponse);
    }
}
