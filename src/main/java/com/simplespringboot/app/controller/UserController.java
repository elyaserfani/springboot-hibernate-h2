package com.simplespringboot.app.controller;


import com.simplespringboot.app.entity.Role;
import com.simplespringboot.app.exception.ErrorResponse;
import com.simplespringboot.app.global.RoleEnum;
import com.simplespringboot.app.entity.User;
import com.simplespringboot.app.dto.request.LoginRequest;
import com.simplespringboot.app.dto.request.RegisterRequest;
import com.simplespringboot.app.dto.response.JwtResponse;
import com.simplespringboot.app.repository.RoleRepository;
import com.simplespringboot.app.service.user.UserService;
import com.simplespringboot.app.service.user.detail.UserDetailsImpl;
import com.simplespringboot.app.utility.JwtUtils;
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
    AuthenticationManager authenticationManager;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Login and authenticate user")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User logged in", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class)) }),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Wrong credentials", content = {@Content(mediaType = "application/json")}),
    })
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User registered", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Username is already taken", content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = ErrorResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Role not found", content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public Object registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userService.existsByUsername(registerRequest.getUsername())) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST,"Username already taken");
        }
        User user = new User(registerRequest.getUsername(), passwordEncoder.encode(registerRequest.getPassword()));
        Set<String> strRoles = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Role not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Role not found"));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Role not found"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return new ErrorResponse(HttpStatus.CREATED,"User Created");
    }
}