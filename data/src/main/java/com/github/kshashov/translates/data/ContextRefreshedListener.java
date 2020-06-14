package com.github.kshashov.translates.data;

import com.github.kshashov.translates.data.enums.PermissionType;
import com.github.kshashov.translates.data.enums.RoleType;
import com.github.kshashov.translates.data.repos.PermissionsRepository;
import com.github.kshashov.translates.data.repos.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    private final PermissionsRepository permissionsRepository;
    private final RolesRepository rolesRepository;

    @Autowired
    public ContextRefreshedListener(PermissionsRepository permissionsRepository, RolesRepository rolesRepository) {
        this.permissionsRepository = permissionsRepository;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // Check permissions
        for (PermissionType permissionType : PermissionType.values()) {
            if (permissionsRepository.findOneByCode(permissionType.getCode()).isEmpty()) {
                throw new IllegalStateException(String.format("Permission '%s' is missing in the database", permissionType.getCode()));
            }
        }

        // Check roles
        for (RoleType roleType : RoleType.values()) {
            if (rolesRepository.findOneByCode(roleType.getCode()).isEmpty()) {
                throw new IllegalStateException(String.format("Role '%s' is missing in the database", roleType.getCode()));
            }
        }
    }
}
