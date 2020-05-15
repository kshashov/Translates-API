package com.github.kshashov.translates.data.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    ADMIN("admin");

    private final String code;
}
