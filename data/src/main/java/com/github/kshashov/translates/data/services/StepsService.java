package com.github.kshashov.translates.data.services;

import com.github.kshashov.translates.data.entities.Step;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public interface StepsService {
    List<Step> replaceExerciseSteps(Long exerciseId, List<StepInfo> stepsInfo);

    @Getter
    @Setter
    class StepInfo {
        private String text;
        private List<AnswerInfo> answers;
        private List<WordInfo> words;
    }

    @Getter
    @Setter
    class AnswerInfo {
        private String text;
    }

    @Getter
    @Setter
    class WordInfo {
        private String source;
        private String translation;
    }
}
