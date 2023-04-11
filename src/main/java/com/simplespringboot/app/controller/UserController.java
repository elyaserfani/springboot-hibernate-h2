package com.simplespringboot.app.controller;


import com.fanapium.keylead.client.exception.ClientOperationException;
import com.fanapium.keylead.common.oauth.exception.OAuthException;
import com.simplespringboot.app.exception.ErrorResponse;
import com.simplespringboot.app.exception.type.InternalServerErrorException;
import com.simplespringboot.app.exception.type.NotFoundException;
import com.simplespringboot.app.dto.request.LoginRequestDto;
import com.simplespringboot.app.dto.request.RegisterRequestDto;
import com.simplespringboot.app.dto.response.JwtResponseDto;
import com.simplespringboot.app.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth",description = "Auth Controller")
public class UserController {


    @Autowired
    UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Login and authenticate user")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User logged in", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponseDto.class)) }),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Wrong credentials", content = {@Content(mediaType = "application/json")}),
    })
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
     return userService.loginUser(loginRequestDto);
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User registered", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Username is already taken", content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = ErrorResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Role not found", content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) throws NotFoundException {
        return userService.registerUser(registerRequestDto);
    }

    @GetMapping("/internalerror")
    @Operation(summary = "Throw internal server error")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<?> throwInternalError(){
        return userService.throwInternalError();
    }

}