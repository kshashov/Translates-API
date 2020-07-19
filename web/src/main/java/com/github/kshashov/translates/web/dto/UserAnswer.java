package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAnswer {
    private Long userId;
    private Long stepId;
    private String text;
    private Boolean success;
}
