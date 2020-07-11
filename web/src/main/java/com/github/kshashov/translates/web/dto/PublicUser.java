package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicUser {
    private Long id;
    private String name;
    private Role role;

    public static PublicUser of(com.github.kshashov.translates.data.entities.User user) {
        if (user == null) return null;

        PublicUser u = new PublicUser();
        u.setId(user.getId());
        u.setName(user.getName());
        u.setRole(Role.of(user.getRole()));

        return u;
    }
}
