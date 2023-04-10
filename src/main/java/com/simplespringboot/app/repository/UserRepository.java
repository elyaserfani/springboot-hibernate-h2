package com.simplespringboot.app.repository;

import com.simplespringboot.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}