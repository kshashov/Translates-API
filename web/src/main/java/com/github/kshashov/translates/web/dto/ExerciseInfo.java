package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class ExerciseInfo {
    @NotBlank(message = "Title is mandatory")
    @Size(min = 3, max = 100)
    private String title;
    @NotNull
    private Long creator;
    @NotNull
    private Long from;
    @NotNull
    private Long to;
    @NotNull
    private Set<Long> tags;
}
