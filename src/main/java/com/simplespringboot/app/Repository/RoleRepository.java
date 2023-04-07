package com.simplespringboot.app.Repository;

import com.simplespringboot.app.Model.Role;
import com.simplespringboot.app.Global.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}