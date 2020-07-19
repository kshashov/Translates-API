package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatsExercise extends Exercise {
    private int userScore;
    private int stepsTotal;
}
