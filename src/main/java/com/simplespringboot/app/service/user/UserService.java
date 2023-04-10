package com.simplespringboot.app.service.user;

import com.simplespringboot.app.entity.User;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}
