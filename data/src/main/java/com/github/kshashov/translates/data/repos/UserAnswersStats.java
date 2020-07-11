package com.github.kshashov.translates.data.repos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAnswersStats {
    private Long exerciseId;
    private Long stepId;
    private Boolean success;
}
