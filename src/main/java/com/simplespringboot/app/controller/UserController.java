package com.simplespringboot.app.controller;


import com.simplespringboot.app.exception.ErrorResponse;
import com.simplespringboot.app.exception.type.NotFoundException;
import com.simplespringboot.app.dto.request.LoginRequestDto;
import com.simplespringboot.app.dto.request.RegisterRequestDto;
import com.simplespringboot.app.dto.response.JwtResponseDto;
import com.simplespringboot.app.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserController {


    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "Login user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User logged in", response = JwtResponseDto.class),
            @ApiResponse(code = 401, message = "Wrong credentials", response = ErrorResponse.class)
    })
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
     return userService.loginUser(loginRequestDto);
    }

    @PostMapping("/register")
    @ApiOperation(value = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User registered", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Username is already taken", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Role not found", response = ErrorResponse.class),
    })
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) throws NotFoundException {
        return userService.registerUser(registerRequestDto);
    }

    @GetMapping("/internalerror")
    @ApiOperation(value = "Throw internal server error")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)
    })
    public ResponseEntity<?> throwInternalError(){
        return userService.throwInternalError();
    }

}