package com.github.kshashov.translates.data.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionType {
    SOLVE_EXERCISES("solve_exercises"),
    COMMENT_EXERCISES("comment_exercises"),
    MANAGE_EXERCISES("manage_exercises"),
    EDIT_PROJECT_USERS("edit_project_users");

    private final String code;
}
