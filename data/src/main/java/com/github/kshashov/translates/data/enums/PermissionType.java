package com.github.kshashov.translates.data.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionType {
    SOLVE_EXERCISES("SOLVE_EXERCISES"),
    COMMENT_EXERCISES("COMMENT_EXERCISES"),
    MANAGE_EXERCISES("MANAGE_EXERCISES"),
    MANAGE_USERS("MANAGE_USERS");

    private final String code;
}
