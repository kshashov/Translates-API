package com.github.kshashov.translates.web.services;

import com.github.kshashov.translates.data.repos.RolesRepository;
import com.github.kshashov.translates.web.dto.Role;
import com.github.kshashov.translates.web.dto.mappings.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiRolesServiceImpl implements ApiRolesService {
    private final RolesRepository rolesRepository;
    private final DtoMapper mapper;

    @Autowired
    public ApiRolesServiceImpl(RolesRepository rolesRepository, DtoMapper mapper) {
        this.rolesRepository = rolesRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Role> getRoles() {
        return mapper.toRole(rolesRepository.findAll());
    }
}
