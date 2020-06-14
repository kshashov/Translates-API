package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role {
    private Long id;
    private String title;
    private String description;

    public static Role of(com.github.kshashov.translates.data.entities.Role role) {
        if (role == null) return null;

        Role r = new Role();
        r.setId(role.getId());
        r.setTitle(role.getTitle());
        r.setDescription(role.getDescription());

        return r;
    }
}
