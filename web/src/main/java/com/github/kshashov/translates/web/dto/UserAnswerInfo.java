package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserAnswerInfo {
    @NotNull(message = "User Id is required")
    private Long userId;
    @NotNull(message = "Step Id is required")
    private Long stepId;
    @NotBlank(message = "Text is empty")
    private String text;
}
