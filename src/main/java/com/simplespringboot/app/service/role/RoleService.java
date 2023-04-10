package com.simplespringboot.app.service.role;

import com.simplespringboot.app.entity.Role;
import com.simplespringboot.app.global.RoleEnum;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(RoleEnum name);
}
