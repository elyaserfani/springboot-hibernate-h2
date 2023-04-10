package com.simplespringboot.app.utility;

import com.simplespringboot.app.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;

public class Utility {
    public static ResponseEntity<?> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
