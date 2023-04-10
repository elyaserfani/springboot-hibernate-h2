package com.simplespringboot.app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.simplespringboot.app.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    ObjectWriter objectWriter;

    public CustomAuthenticationEntryPoint() {
        this.objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED,"The user is not authenticated");
        String json = objectWriter.writeValueAsString(errorResponse);
        response.getOutputStream().println(json);
    }
}