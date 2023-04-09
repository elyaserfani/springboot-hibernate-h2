package com.simplespringboot.app.repository;

import com.simplespringboot.app.entity.Role;
import com.simplespringboot.app.global.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}