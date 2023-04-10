package com.simplespringboot.app.service.role;

import com.simplespringboot.app.entity.Role;
import com.simplespringboot.app.global.RoleEnum;
import com.simplespringboot.app.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Optional<Role> findByName(RoleEnum name) {
        return roleRepository.findByName(name);
    }
}
