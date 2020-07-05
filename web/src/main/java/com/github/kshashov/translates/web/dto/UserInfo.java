package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserInfo {
    @Size(min = 3, max = 100)
    @NotBlank(message = "User Name is empty")
    private String name;
}
