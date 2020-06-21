package com.github.kshashov.translates.web.controllers;

import com.github.kshashov.translates.web.dto.Role;
import com.github.kshashov.translates.web.services.ApiRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    private final ApiRolesService rolesService;

    @Autowired
    public RolesController(ApiRolesService rolesService) {
        this.rolesService = rolesService;
    }

    @GetMapping("")
    public List<Role> getRoles() {
        return rolesService.getRoles();
    }
}
