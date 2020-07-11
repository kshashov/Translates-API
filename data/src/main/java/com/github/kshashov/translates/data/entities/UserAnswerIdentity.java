package com.github.kshashov.translates.data.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Embeddable
public class UserAnswerIdentity implements Serializable {

    @NotNull
    @Column(name = "user_id", updatable = false)
    private Long userId;

    @NotNull
    @Column(name = "step_id", updatable = false)
    private Long stepId;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
