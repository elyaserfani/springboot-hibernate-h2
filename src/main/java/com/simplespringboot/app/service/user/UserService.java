package com.simplespringboot.app.service.user;

import com.simplespringboot.app.dto.request.LoginRequest;
import com.simplespringboot.app.dto.request.RegisterRequest;
import com.simplespringboot.app.dto.response.JwtResponse;
import com.simplespringboot.app.entity.Role;
import com.simplespringboot.app.entity.User;
import com.simplespringboot.app.exception.ErrorResponse;
import com.simplespringboot.app.exception.type.NotFoundException;
import com.simplespringboot.app.global.RoleEnum;
import com.simplespringboot.app.repository.RoleRepository;
import com.simplespringboot.app.repository.UserRepository;
import com.simplespringboot.app.service.user.detail.UserDetailsImpl;
import com.simplespringboot.app.utility.JwtUtils;
import com.simplespringboot.app.utility.Utility;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    public ResponseEntity<?> registerUser(RegisterRequest registerRequest) throws NotFoundException{
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return Utility.buildResponseEntity(new ErrorResponse(HttpStatus.BAD_REQUEST,"Username already taken"));
        }
        User user = new User(registerRequest.getUsername(), passwordEncoder.encode(registerRequest.getPassword()));
        Set<String> strRoles = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN).orElseThrow(() -> new NotFoundException("Role not found"));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER).orElseThrow(() -> new NotFoundException("Role not found"));
                        roles.add(userRole);
                }
            });
        user.setRoles(roles);
        userRepository.save(user);
        return Utility.buildResponseEntity(new ErrorResponse(HttpStatus.CREATED,"User created"));
    }
    public ResponseEntity<?> loginUser(LoginRequest loginRequest){
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
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
