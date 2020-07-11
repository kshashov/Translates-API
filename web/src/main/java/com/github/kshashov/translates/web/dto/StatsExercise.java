package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatsExercise extends Exercise {
    private int userScore;
    private int stepsTotal;

    public static StatsExercise of(com.github.kshashov.translates.data.entities.Exercise exercise) {
        return (StatsExercise) of(new StatsExercise(), exercise);
    }
}
