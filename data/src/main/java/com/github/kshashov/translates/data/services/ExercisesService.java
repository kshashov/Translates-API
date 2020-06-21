package com.github.kshashov.translates.data.services;

import com.github.kshashov.translates.data.entities.Exercise;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public interface ExercisesService {
    Exercise createExercise(ExerciseInfo exercise);

    void updateExercise(Long id, ExerciseInfo exerciseInfo);

    void delete(Long id);

    @Getter
    @Setter
    class ExerciseInfo {
        private String title;
        private Long from;
        private Long to;
        private Long creator;
        private Set<Long> tags;
    }
}
