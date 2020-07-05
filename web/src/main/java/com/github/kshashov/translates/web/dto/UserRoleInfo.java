package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserRoleInfo {
    @NotNull(message = "User Role Id is required")
    private Long id;
}
