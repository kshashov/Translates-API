package com.github.kshashov.translates.web.services;

import com.github.kshashov.translates.web.dto.Role;

import java.util.List;

public interface SecuredRolesService {
    List<Role> getRoles();
}
