package com.github.kshashov.translates.web.services;

import com.github.kshashov.translates.web.dto.Step;
import com.github.kshashov.translates.web.dto.StepInfo;

import java.util.List;

public interface ApiStepsService {
    List<Step> replaceExerciseSteps(Long exerciseId, List<StepInfo> info);

    List<Step> getByExercise(Long exerciseId);
}
