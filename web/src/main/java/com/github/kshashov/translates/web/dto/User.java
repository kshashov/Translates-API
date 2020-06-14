package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String email;
    private String name;
    private Role role;

    public static User of(com.github.kshashov.translates.data.entities.User user) {
        if (user == null) return null;

        User u = new User();
        u.setId(user.getId());
        u.setEmail(user.getEmail());
        u.setName(user.getName());
        u.setRole(Role.of(user.getRole()));

        return u;
    }
}
