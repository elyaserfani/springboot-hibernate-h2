package com.simplespringboot.app.controller;


import com.simplespringboot.app.entity.Role;
import com.simplespringboot.app.exception.ErrorResponse;
import com.simplespringboot.app.exception.type.NotFoundException;
import com.simplespringboot.app.global.RoleEnum;
import com.simplespringboot.app.entity.User;
import com.simplespringboot.app.dto.request.LoginRequest;
import com.simplespringboot.app.dto.request.RegisterRequest;
import com.simplespringboot.app.dto.response.JwtResponse;
import com.simplespringboot.app.repository.RoleRepository;
import com.simplespringboot.app.service.role.RoleService;
import com.simplespringboot.app.service.user.UserService;
import com.simplespringboot.app.service.user.detail.UserDetailsImpl;
import com.simplespringboot.app.utility.JwtUtils;
import com.simplespringboot.app.utility.Utility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User logged in", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class)) }),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Wrong credentials", content = {@Content(mediaType = "application/json")}),
    })
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
     return userService.loginUser(loginRequest);
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User registered", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Username is already taken", content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = ErrorResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Role not found", content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) throws NotFoundException {
        return userService.registerUser(registerRequest);
    }

}