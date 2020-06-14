package com.github.kshashov.translates.web.services;

import com.github.kshashov.translates.data.repos.RolesRepository;
import com.github.kshashov.translates.web.dto.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecuredRolesServiceImpl implements SecuredRolesService {
    private final RolesRepository rolesRepository;

    @Autowired
    public SecuredRolesServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.github.kshashov.translates.data.enums.PermissionType).MANAGE_USERS.getCode())")
    public List<Role> getRoles() {
        return rolesRepository.findAll().stream()
                .map(Role::of)
                .collect(Collectors.toList());
    }
}
