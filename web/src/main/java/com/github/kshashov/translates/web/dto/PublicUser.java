package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicUser {
    private Long id;
    private String name;
    private String client;
    private Role role;
}
