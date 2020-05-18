package com.github.kshashov.translates.data.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    ADMIN("admin"),
    USER("user");

    private final String code;
}
