package com.github.kshashov.translates.data.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    ADMIN("ADMIN"),
    USER("USER");

    private final String code;
}
