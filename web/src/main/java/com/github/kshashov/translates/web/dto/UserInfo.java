package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserInfo {
    @NotBlank(message = "Name is mandatory")
    private String name;
}
