package com.github.kshashov.translates.data.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "user_answers", schema = "public")
public class UserAnswer {

    @EmbeddedId
    private UserAnswerIdentity identity;

    @NotNull
    @Column(name = "text", updatable = false)
    private String text;

    @NotNull
    @Column(name = "success", updatable = false)
    private Boolean success;

    @MapsId("step_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Step step;
}
